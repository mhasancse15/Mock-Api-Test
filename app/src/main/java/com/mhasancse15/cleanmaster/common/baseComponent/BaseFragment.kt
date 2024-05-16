package com.mhasancse15.cleanmaster.common.baseComponent

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseFragment<V : ViewBinding> : Fragment() {

    private var _binding: V? = null
    private val binding get() = _binding!!
    protected abstract fun viewBindingLayout(): V
    protected abstract fun initializeView(savedInstanceState: Bundle?)
    private var handler: Handler? = null

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = viewBindingLayout()
        initializeView(savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        return binding.root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressedCallback.isEnabled = true
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressedCallback.isEnabled = false
    }

    protected fun showProgressBar(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
            requireActivity().window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            view.visibility = View.GONE
            requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    protected fun showLongToast(msg: String?) {
        val context = context ?: return
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

    }

    protected fun showShortToast(msg: String?) {
        val context = context ?: return
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler!!.removeCallbacksAndMessages(null)
        _binding = null
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    collect {
                        action(it)
                    }
                }
            }
        }
    }

    fun execute(job: suspend () -> Unit) {
        lifecycleScope.launch {
            job.invoke()
        }
    }

    protected fun onBackPressed() {
        val view = view
        if (view != null) {
            Navigation.findNavController(view).navigateUp()
        }
    }
}