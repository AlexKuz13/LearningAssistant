package com.example.learningassistant.ui.fragments.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.learningassistant.databinding.FragmentRatingBinding
import com.example.learningassistant.models.User
import com.example.learningassistant.utilits.APP_ACTIVITY
import com.example.learningassistant.utilits.showToast



class RatingFragment : DialogFragment() {

    private var _binding: FragmentRatingBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var user: User
    private lateinit var mViewModel: RatingFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRatingBinding.inflate(layoutInflater, container, false)
        user = arguments?.getSerializable("User") as User
        return mBinding.root
    }

    // Осталось messages fragment , придумать что то с меню, сделать заставку, изменить дизайн, допиливать фишечки

    override fun onResume() {
        super.onResume()

        mBinding.btnRate.setOnClickListener {
            mViewModel =
                ViewModelProvider(this, RatingViewModelFactory(user.id, mBinding.ratingBar.rating))
                    .get(RatingFragmentViewModel::class.java)
            mViewModel.rateUser {
                showToast("Спасибо за оценку!")
                //menuRating.clear()
                APP_ACTIVITY.navController.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}