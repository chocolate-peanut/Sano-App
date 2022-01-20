package com.example.sano;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class GoalFragment extends Fragment {

    RecyclerView goalList;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter<GoalModel, GoalViewHolder> goalAdapter;
    String goal_checked;

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
                //retrieve goal content
                goalViewHolder.goal_content.setText(goalModel.getGoal());
                //trying to retrieve goal checkbox value
                if (goalModel.getGoalCheckbox() == "true"){
                    goalViewHolder.goal_check.setChecked(true);
                }
                else{
                    goalViewHolder.goal_check.setChecked(false);
                }

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

                //cuz checkbox is under goal_rview.xml so i do save checkbox data here
                CheckBox goal_check_box = (CheckBox) view.findViewById(R.id.goal_check);
                DocumentReference documentReference = firestore.collection("goals").document();
                Map<String, Object> goalCheck = new HashMap<>();

                goal_check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (goal_check_box.isChecked()){
                            goal_check_box.setChecked(true);
                            goal_checked = "true";
                            goalCheck.put("GoalCheckbox", goal_checked); //store the "true" into firebase
                            documentReference.set(goalCheck, SetOptions.merge());

                        }
                        else{
                            goal_check_box.setChecked(false);
                            goal_checked = "false"; //here i think false nonid to save?
                            //goalCheck.put("GoalCheckbox", goal_checked);
                            //documentReference.set(goalCheck);
                        }
                    }

                });

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
        CheckBox goal_check;
        View view;

        public GoalViewHolder (@NonNull View itemView){
            super(itemView);

            goal_content = itemView.findViewById(R.id.goal_content);
            goal_check = itemView.findViewById(R.id.goal_check);
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