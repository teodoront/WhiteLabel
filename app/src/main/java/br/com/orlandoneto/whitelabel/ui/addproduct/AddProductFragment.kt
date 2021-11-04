package br.com.orlandoneto.whitelabel.ui.addproduct
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import br.com.orlandoneto.whitelabel.databinding.AddProductFragmentBinding
import br.com.orlandoneto.whitelabel.util.CurrencyTextWatcher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding: AddProductFragmentBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it
        binding.imageProduct.setImageURI(it)

    }

    private val viewModel: AddProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVmEvents()
        setListeners()
    }

    private fun observeVmEvents(){
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner){drawableResId ->

            binding.imageProduct.setBackgroundResource(drawableResId!!)
        }

        viewModel.descriptionFieldErrorResId.observe(viewLifecycleOwner){stringResId ->
            binding.inputLayoutDescription.setError(stringResId)
        }

        viewModel.priceFieldErrorResId.observe(viewLifecycleOwner){stringResId ->
            binding.inputLayoutPrice.setError(stringResId)
        }
    }

    private fun TextInputLayout.setError(stringResId: Int?){
        error = if (stringResId != null){
            getString(stringResId)
        }else null
    }



    private fun setListeners(){
        binding.imageProduct.setOnClickListener {
            chooseImage()

        }

        binding.buttonAddProduct.setOnClickListener {
            val description = binding.inputDescription.text.toString()
            val price = binding.inputPrice.text.toString()

            viewModel.createProduct(description, price, imageUri)

        }

        binding.inputPrice.run {

            addTextChangedListener(CurrencyTextWatcher(this))

        }
    }

    private fun chooseImage(){
        getContent.launch("image/*")
    }

}