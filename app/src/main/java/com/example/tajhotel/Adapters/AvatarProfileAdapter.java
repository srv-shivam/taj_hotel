package com.example.tajhotel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tajhotel.MainActivity;
import com.example.tajhotel.R;

import java.util.ArrayList;

public class AvatarProfileAdapter extends RecyclerView.Adapter<AvatarProfileAdapter.Viewholder> {

    Context context;
    ArrayList<Integer> avatarArrayList;
    Button confirmBtn;
    int selectedAvatar = 0;

    public AvatarProfileAdapter(Context context, ArrayList<Integer> avatarArrayList, Button confirmBtn) {
        this.context = context;
        this.avatarArrayList = avatarArrayList;
        this.confirmBtn = confirmBtn;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.avatar_profile_layout_design, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(AvatarProfileAdapter.Viewholder holder, int position) {
        holder.profileAvatar.setImageResource(avatarArrayList.get(position));

        if (selectedAvatar == position) {
            holder.cardView.animate().scaleX(1.1f);
            holder.cardView.animate().scaleY(1.1f);
            holder.llBackground.setBackgroundResource(R.color.yellow);
        } else {
            holder.cardView.animate().scaleX(1f);
            holder.cardView.animate().scaleY(1f);
            holder.llBackground.setBackgroundResource(R.color.white);
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Profile Image changed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Selected Avatar", avatarArrayList.get(selectedAvatar));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return avatarArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public ImageView profileAvatar;
        public CardView cardView;
        public LinearLayout llBackground;

        public Viewholder(View itemView) {
            super(itemView);
            profileAvatar = itemView.findViewById(R.id.avatar);
            cardView = itemView.findViewById(R.id.avatar_card_view);
            llBackground = itemView.findViewById(R.id.avatar_linear_layout);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedAvatar = getAdapterPosition();
                    confirmBtn.setEnabled(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
