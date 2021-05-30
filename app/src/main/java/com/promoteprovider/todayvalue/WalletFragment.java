package com.promoteprovider.todayvalue;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.promoteprovider.todayvalue.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User_data user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentWalletBinding.inflate(inflater,container,false);
        database = FirebaseFirestore.getInstance();
        database.collection("user")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            user = documentSnapshot.toObject(User_data.class);
                binding.currentCoins.setText(String.valueOf(user.getCoins()));
            }
        });

        binding.sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getCoins() >= 50000){
                    String uid = FirebaseAuth.getInstance().getUid();
                    String paypal,bkash;
                   paypal = binding.emailBox.getText().toString();
//                   bkash = binding.bkashBox.getText().toString();
                    WithdrawRequest request = new WithdrawRequest(uid,paypal,user.getName());
                    database.collection("withdraws")
                            .document(uid)
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Request Sent Successfully.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "You need more Coins to withdraw", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }
}