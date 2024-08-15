package com.shivam.recipe.presentation.addRecipeScreen

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.shivam.recipe.R
import com.shivam.recipe.databinding.FragmentAddRecipeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class addRecipeFragment : Fragment() {

    companion object {
        fun newInstance() = addRecipeFragment()
    }

    private var _binding : FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
        _binding = FragmentAddRecipeBinding.inflate(inflater,container,false)

        // adding viewmodel to binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // selecting image from gallery
        val imagePick = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri ->
            if(uri == null){
                Toast.makeText(this.requireContext(), "please select an image", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }
//            binding.recipeImg.setImageURI(uri)
            viewModel.setImageUri(uri.toString())
        }

        // setting on click listener to image view to pick image
        binding.recipeImg.setOnClickListener {
            imagePick.launch("image/*")
        }

        // setting on click listener to add recipe button
        binding.saveRecipeBtn.setOnClickListener {
            if (binding.recipeName.text.isNotBlank()
                && binding.recipeCategory.text.isNotBlank()
                && binding.recipeIngrediants.text.isNotBlank()
                && binding.recipeInstruction.text.isNotBlank()
                && viewModel.imageUri.value?.isNotBlank()!!
                ){
                viewModel.saveRecipe()
                findNavController().navigateUp()
                Toast.makeText(this.requireContext(), "dataSuccessfully uploaded to the firebase", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this.requireContext(), "please fill all the fields", Toast.LENGTH_SHORT).show()
            }
            
        }
        
        
        // observing the error live data if any exceptions happens in firebase operation
        viewModel.error.observe(this.viewLifecycleOwner){
            errorMsg -> Toast.makeText(this.requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
            viewModel.errorClear()
        }
        

        return binding.root
    }

    
    


}