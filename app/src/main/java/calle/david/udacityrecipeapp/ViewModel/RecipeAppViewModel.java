package calle.david.udacityrecipeapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;


import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.Data.RecipeAppRepo;

public class RecipeAppViewModel extends ViewModel {
    private final RecipeAppRepo mRepo;
    private final LiveData<List<Recipe>> mRecipeList;
    private  LiveData<List<Ingredients>> mIngredientsList;
    private  LiveData<List<Steps>> mSteplist;
    private MutableLiveData<Recipe> mSelectedRecipe;
    private MutableLiveData<Steps> mFocusedStep;
    private long videoPosition;





    RecipeAppViewModel(RecipeAppRepo repo){
        this.mRepo = repo;
        this.mRecipeList = mRepo.getRecipeList();
        this.mSelectedRecipe = new MutableLiveData<>();
        this.mFocusedStep = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return mRecipeList;
    }


    public LiveData<List<Ingredients>> getIngredientsforRecipe(int recipeID){
       //if(mIngredientsList == null) mIngredientsList = mRepo.getIngredientsList(recipeID);
       return mIngredientsList;
    }
    public LiveData<List<Steps>> getStepsforRecipe(int recipeID){
      // if(mSteplist==null) mSteplist = mRepo.getStepsList(recipeID);
        return mSteplist;
    }
    public LiveData<List<Steps>> getFetchedSteps(){
        return mSteplist;
    }
    public void setSelectedRecipe(Recipe recipe){
        mIngredientsList = mRepo.getIngredientsList(recipe.getId());
        mSteplist = mRepo.getStepsList(recipe.getId());
        mSelectedRecipe.setValue(recipe);
    }
    public MutableLiveData<Recipe> getSelectedRecipe(){
        return mSelectedRecipe;
    }

    public MutableLiveData<Steps> getFocusedStep() {
        return mFocusedStep;
    }


    public long getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(long videoPosition) {
        this.videoPosition = videoPosition;
    }
}
