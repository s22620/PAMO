package com.example.bmicalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class DietRecommendationFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private double userCalories = 2000.0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diet_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView recommendationTextView = view.findViewById(R.id.recommendationText);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getDailyCalories().observe(getViewLifecycleOwner(), calories -> {
            userCalories = calories;
            displayRecommendations(userCalories, recommendationTextView);
        });

    }

    private void displayRecommendations(double userCalories, TextView textView) {
        List<Recipe> recommendedRecipes = getRecommendedRecipes(userCalories);
        StringBuilder recommendations = new StringBuilder();

        if (recommendedRecipes.isEmpty()) {
            recommendations.append("No matching recipes found.");
        } else {
            for (Recipe recipe : recommendedRecipes) {
                recommendations.append("🍽 ").append(recipe.getName()).append("\n")
                        .append(recipe.getDescription()).append("\n\n");
            }
        }

        textView.setText(recommendations.toString());
    }

    private List<Recipe> getRecommendedRecipes(double userCalories) {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe("Avocado Toast",
                "Whole grain toast with mashed avocado, poached egg, and cherry tomatoes.",
                Integer.MIN_VALUE, 2000,
                Arrays.asList("Whole grain toast", "Avocado", "Egg", "Cherry tomatoes")));

        recipes.add(new Recipe("Greek Yogurt Parfait",
                "Layers of Greek yogurt, granola, and mixed fruits.",
                Integer.MIN_VALUE, 2000,
                Arrays.asList("Greek yogurt", "Granola", "Mixed fruits")));

        recipes.add(new Recipe("Turkey and Veggie Stir Fry",
                "Lean ground turkey with colorful vegetables served with brown rice.",
                2000, 2500,
                Arrays.asList("Ground turkey", "Bell peppers", "Carrots", "Broccoli", "Brown rice")));

        recipes.add(new Recipe("Lentil Soup with Whole Grain Bread",
                "Hearty lentil soup rich in protein and fiber, served with bread.",
                2000, 2500,
                Arrays.asList("Lentils", "Vegetable broth", "Onion", "Garlic", "Whole grain bread")));

        recipes.add(new Recipe("Beef Burrito Bowl",
                "Lean beef with rice, black beans, corn, and avocado in a bowl.",
                2500, Integer.MAX_VALUE,
                Arrays.asList("Lean beef", "Rice", "Black beans", "Corn", "Avocado")));

        recipes.add(new Recipe("Peanut Butter Banana Toast",
                "Toasted bread with peanut butter and banana slices, sprinkled with chia seeds.",
                2500, Integer.MAX_VALUE,
                Arrays.asList("Bread", "Peanut butter", "Banana", "Chia seeds")));

        List<Recipe> filteredRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.matchesCalorieRange(userCalories)) {
                filteredRecipes.add(recipe);
            }
        }

        return filteredRecipes;
    }
}