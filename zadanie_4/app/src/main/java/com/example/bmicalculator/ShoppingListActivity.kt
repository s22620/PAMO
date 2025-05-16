package com.example.bmicalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmicalculator.databinding.ActivityShoppingListBinding

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingListBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // Wybieramy przepis (przykładowo: Avocado Toast)
        val selectedRecipe = "Avocado Toast"
        val ingredients = getIngredientsForRecipe(selectedRecipe)

        ingredients.forEach { itemName ->
            todoAdapter.addTodo(Todo(itemName))
        }
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(mutableListOf())
        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(this)
    }

    // 📋 Lista składników przypisana do nazw przepisów
    private fun getIngredientsForRecipe(recipeName: String): List<String> {
        return when (recipeName) {
            "Avocado Toast" -> listOf("Whole grain toast", "Avocado", "Egg", "Cherry tomatoes")
            "Greek Yogurt Parfait" -> listOf("Greek yogurt", "Granola", "Mixed fruits")
            "Turkey and Veggie Stir Fry" -> listOf("Ground turkey", "Bell peppers", "Carrots", "Broccoli", "Brown rice")
            "Lentil Soup with Whole Grain Bread" -> listOf("Lentils", "Vegetable broth", "Onion", "Garlic", "Whole grain bread")
            "Beef Burrito Bowl" -> listOf("Lean beef", "Rice", "Black beans", "Corn", "Avocado")
            "Peanut Butter Banana Toast" -> listOf("Bread", "Peanut butter", "Banana", "Chia seeds")
            else -> listOf("Brak składników")
        }
    }
}
