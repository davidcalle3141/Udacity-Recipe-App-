package calle.david.udacityrecipeapp.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Adapters.IngredientListAdapter;
import calle.david.udacityrecipeapp.UI.Adapters.StepListAdapter;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;


public class RecipeIngredientsFragment extends Fragment implements StepListAdapter.StepListAdapterOnClickListener {
    private RecipeAppViewModel mViewModel;
    private Context mContext;
    private View view;
    private int recipeID;
    //private String recipeImage;
   // private String recipeName;

    IngredientListAdapter ingredientListAdapter;
    StepListAdapter stepListAdapter;

    @BindView(R.id.ingredientsListRV)RecyclerView recyclerViewIngredientsList;
    @BindView(R.id.ingredientsListStepsRV)RecyclerView recyclerViewStepDescription;
    @BindView(R.id.ingredients_card_name)TextView recipeName;
    @BindView(R.id.ingredients_card_image)ImageView recipeImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_recipe_ingredients_fragment,container,false);
        this.mContext = getContext();

        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManagerIng = new LinearLayoutManager(mContext);
        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(mContext);

        recyclerViewStepDescription.setNestedScrollingEnabled(false);
        recyclerViewIngredientsList.setNestedScrollingEnabled(false);
        recyclerViewStepDescription.setLayoutManager(layoutManagerSteps);
        recyclerViewIngredientsList.setLayoutManager(layoutManagerIng);
        stepListAdapter = new StepListAdapter(mContext,this);
        ingredientListAdapter = new IngredientListAdapter(mContext);
        recyclerViewStepDescription.setAdapter(stepListAdapter);
        recyclerViewIngredientsList.setAdapter(ingredientListAdapter);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);


        populateUI();
        }

    private void populateUI() {
        //mViewModel.getIngredientsforRecipe(recipeID).removeObservers(this);


        if (getArguments() != null) {
            this.recipeID = getArguments().getInt("recipeID");
            recipeName.setText(getArguments().getString("recipeName"));
            Picasso.get().load(getArguments().getString("recipeImage")).into(recipeImage);
        }


        mViewModel.getIngredientsforRecipe(recipeID).observe(this, ingredients -> {
            if(ingredients!=null){
                ingredientListAdapter.addIngredientsList(ingredients);
                ingredientListAdapter.notifyDataSetChanged();

            }

        });

        mViewModel.getStepsforRecipe(recipeID).observe(this, stepsList -> {
            if(stepsList!= null){
                stepListAdapter.addStepList(stepsList);
                stepListAdapter.notifyDataSetChanged();
                recyclerViewStepDescription.setVisibility(View.VISIBLE);
            }
        });


    }


    @Override
    public void onClick(int position) {
        mViewModel.getFetchedSteps().observe(this, stepsList -> {
            if(stepsList!= null){
               Bundle bundle = new Bundle();
               bundle.putString("description",stepsList.get(position).getDescription());
               bundle.putString("videoURL", stepsList.get(position).getVideoURL());
               bundle.putString("videoThumbnail", stepsList.get(position).getThumbnailURL());
            }
        });


    }
}