package com.example.sano;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GoalFragment extends Fragment {

    RecyclerView goalList;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter<GoalModel, GoalViewHolder> goalAdapter;

    public GoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goal, container, false);

        Button new_goal_button = (Button) rootView.findViewById(R.id.new_goal_button);
        new_goal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewGoal.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //--- retrieve data
        firestore = FirebaseFirestore.getInstance();

        Query query = firestore.collection("goals").orderBy("Goal", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<GoalModel> allGoals = new FirestoreRecyclerOptions.Builder<GoalModel>()
                .setQuery(query, GoalModel.class)
                .build();

        goalAdapter = new FirestoreRecyclerAdapter<GoalModel, GoalFragment.GoalViewHolder>(allGoals) {
            @Override
            protected void onBindViewHolder(@NonNull GoalViewHolder goalViewHolder, int i, @NonNull GoalModel goalModel) {
                goalViewHolder.goal_content.setText(goalModel.getGoal());

                goalViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(view.getContext(), "You clicked this.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public GoalFragment.GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_rview, parent, false);
                return new GoalFragment.GoalViewHolder(view);
            }

        };
        //---

        goalList = getActivity().findViewById(R.id.goal_recyclerView);
        goalList.setAdapter(goalAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        goalList.setLayoutManager(layoutManager);

    }

    public class GoalViewHolder extends RecyclerView.ViewHolder{
        TextView goal_content;
        View view;

        public GoalViewHolder (@NonNull View itemView){
            super(itemView);

            goal_content = itemView.findViewById(R.id.goal_content);
            view = itemView;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        goalAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        if (goalAdapter != null){
            goalAdapter.stopListening();
        }
    }

}