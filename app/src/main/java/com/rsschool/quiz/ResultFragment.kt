package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var listener: ResultFragmentListener? = null

    interface ResultFragmentListener {
        fun onShareButtonPressed()
        fun onBackButtonClicked()
        fun onCloseButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = arguments?.getInt(RESULT) ?: return
        binding.result.text = "Your result: $result %"

        binding.back.setOnClickListener {
            listener?.onBackButtonClicked()
        }

        binding.close.setOnClickListener {
            listener?.onCloseButtonPressed()
        }

        binding.share.setOnClickListener {
            listener?.onShareButtonPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ResultFragmentListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance(result: Int): ResultFragment {
            val fragment = ResultFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putInt(RESULT, result)
                }
            }
            return fragment
        }

        private const val RESULT = "RESULT"
    }
}