package com.example.tabdab;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class FriendsMenuFragment extends Fragment {
  EditText editEmail;
  Button butDone;
  ScrollView scrollerFoundUsers;
  LinearLayout layoutParent, layoutFoundUsers, layoutFriends;

  User user;

  DatabaseReference emailsDb;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    String userStr = getArguments().getString("user", "");
    user = User.fromJson(userStr);
    emailsDb = FirebaseDatabase.getInstance().getReference("users");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view =  inflater.inflate(R.layout.fragment_friends_menu, container, false);
    editEmail = view.findViewById(R.id.editEmail);
    butDone = view.findViewById(R.id.butSearch);
    scrollerFoundUsers = view.findViewById(R.id.scrollFoundUsers);
    layoutParent = view.findViewById(R.id.layoutParent);
    layoutFoundUsers = view.findViewById(R.id.layoutFoundUsers);
    layoutFriends = view.findViewById(R.id.layoutFriends);

    // If the user has friends display them
    if (user.getFriends().size() != 0) {
      for (int i = 0; i < user.getFriends().size(); i++) {
        // Set the friends information
        LinearLayout friend = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,10);
        friend.setLayoutParams(params);
        friend.setOrientation(LinearLayout.VERTICAL);
        friend.setPadding(5,5,5,5);
        friend.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.textview_pink, null));

        TextView name = new TextView(getContext());
        name.setText(user.getFriends().get(i).getFirstName() + " " + user.getFriends().get(i).getLastName());
        name.setTextColor(Color.WHITE);
        name.setTextSize(30);

        TextView email = new TextView(getContext());
        email.setText(user.getFriends().get(i).getEmail());
        email.setTextColor(Color.WHITE);
        email.setTextSize(20);

        // Add the views together
        friend.addView(name);
        friend.addView(email);
        layoutFriends.addView(friend);
      }
    } else {  // No friends
      TextView noFriends = new TextView(getContext());
      noFriends.setText("No friends yet!");
      noFriends.setTextColor(Color.WHITE);
      layoutFriends.addView(noFriends);
    }

    butDone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Could potentially be to big. Might want to see if it has the email then go straight to that user
        emailsDb.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            String enteredEmail = editEmail.getText().toString().replace('.','*');

            // Check if the entered user exists
            if (snapshot.hasChild(enteredEmail)) {
              final User foundUser = snapshot.child(enteredEmail).getValue(User.class);

              // Create a button for the found user
              Button butUser = new Button(getContext());
              LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                      LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
              butUser.setPadding(5,5,5,5);
              butUser.setText(foundUser.getFirstName() + " " + foundUser.getLastName());
              butUser.setTextColor(Color.WHITE);
              butUser.setAllCaps(false);
              butUser.setBackground(ResourcesCompat.getDrawable(getResources(),
                      R.drawable.button_pink_white_outline, null));

              // Add the user to the friend list if they clock the button
              butUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Friend newFriend = new Friend(foundUser.getFirstName(), foundUser.getLastName(),
                          foundUser.getEmail());

                  // Double check the users are not already friends
                  if (user.getFriends().contains(newFriend)) {
                    Toast.makeText(getContext(), "Already friends.", Toast.LENGTH_SHORT).show();
                  } else {
                    user.addFriend(new Friend(foundUser.getFirstName(), foundUser.getLastName(),
                            foundUser.getEmail()));

                    // Update firebase
                    FirebaseDatabase.getInstance().getReference("users").child(
                            user.getEmail().replace('.', '*')).setValue(user);

                    Toast.makeText(getContext(), "Friend Added!", Toast.LENGTH_SHORT).show();

                    // Update the UI
                    layoutFoundUsers.removeAllViews();
                    editEmail.setText("");
                  }
                }
              });

              layoutFoundUsers.addView(butUser);
            } else {
              TextView userNotFound = new TextView(getContext());
              userNotFound.setText("User e-mail not found.");
              userNotFound.setTextColor(Color.WHITE);
              layoutFoundUsers.addView(userNotFound);
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
        });
      }
    });

    return view;
  }

  public FriendsMenuFragment() {
    // Required empty public constructor
  }
  public static FriendsMenuFragment newInstance() {
    return new FriendsMenuFragment();
  }
  public static FriendsMenuFragment newInstance(User user) {
    FriendsMenuFragment friendsMenuFragment = new FriendsMenuFragment();
    Bundle args = new Bundle();
    args.putString("user", user.toJson());
    friendsMenuFragment.setArguments(args);
    return friendsMenuFragment;
  }
}