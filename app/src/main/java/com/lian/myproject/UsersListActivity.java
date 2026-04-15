package com.lian.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import com.lian.myproject.R;
import com.lian.myproject.adapters.UserAdapter;
import com.lian.myproject.model.User;
import com.lian.myproject.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends com.lian.myproject.BaseActivity {

    private static final String TAG = "UsersListActivity";
    private UserAdapter userAdapter;
    private TextView tvUserCount;

    ArrayList <User> users=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_list);


        RecyclerView rcUsers = findViewById(R.id.rv_users_list);
        tvUserCount = findViewById(R.id.tv_user_count);
        rcUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter( users, new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
//                 Handle user click
                Log.d(TAG, "User clicked: " + user);
                Intent intent = new Intent(UsersListActivity.this, com.lian.myproject.UserProfileActivity.class);
                intent.putExtra("USER_UID", user.getUid());
                startActivity(intent);
            }

            @Override
            public void onLongUserClick(User user) {
//                 Handle long user click
               Log.d(TAG, "User long clicked: " + user);
            }
        });
      rcUsers.setAdapter(userAdapter);
      getUsers();
    }



    protected void getUsers() {

        databaseService.getUserList(new DatabaseService.DatabaseCallback<>() {
            @Override
            public void onCompleted(List<User> users) {
                userAdapter.setUserList(users);
                tvUserCount.setText("Total users: " + users.size());
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get users list", e);
            }
        });
    }

}