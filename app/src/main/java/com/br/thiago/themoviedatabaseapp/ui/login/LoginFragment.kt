package com.br.thiago.themoviedatabaseapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.thiago.themoviedatabaseapp.R
import com.br.thiago.themoviedatabaseapp.databinding.FragmentLoginBinding
import com.br.thiago.themoviedatabaseapp.ui.list.NowPlayingPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginFragment : Fragment(), LoginContract.View {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomNavigation: BottomNavigationView
    private var presenter: LoginPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        bottomNavigation = requireActivity().findViewById(R.id.bottomNavigationView)
        bottomNavigation.visibility = View.GONE
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter?.destroyView()
        presenter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(this)
        presenter?.onCurrentUserLogin()
        binding.btnLogin.setOnClickListener {
            val user = binding.tfUser.editText?.text.toString()
            val password = binding.tfPassword.editText?.text.toString()
            presenter?.login(user, password)
        }
    }

    override fun onSuccessfulLogin() {
        bottomNavigation.visibility = View.VISIBLE
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragment2ToNowPlayingFragment()
        )
    }

}