package com.example.cashplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cashplan.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    // View Binding instance for the fragment's layout
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IMAGE = "image"

        // Factory method to create a new fragment instance with data
        fun newInstance(title: String, description: String, imageRes: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESCRIPTION, description)
                putInt(ARG_IMAGE, imageRes)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve arguments and populate views using View Binding
        arguments?.let {
            binding.titleText.text = it.getString(ARG_TITLE)
            binding.descriptionText.text = it.getString(ARG_DESCRIPTION)
            binding.illustrationImage.setImageResource(it.getInt(ARG_IMAGE))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to prevent memory leaks
        _binding = null
    }
}
