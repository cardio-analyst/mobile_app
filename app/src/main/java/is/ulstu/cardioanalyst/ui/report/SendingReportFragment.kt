package `is`.ulstu.cardioanalyst.ui.report

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentSendReportBinding
import javax.inject.Inject

@AndroidEntryPoint
class SendingReportFragment @Inject constructor() : BaseFragment(R.layout.fragment_send_report) {

    override val viewModel by viewModels<SendingReportViewModel>()

    private val binding by viewBinding(FragmentSendReportBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_send_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            cancelTextView.paintFlags = emailTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            sendButton.setOnClickListener {
                val receiver = emailTextEdit.text.toString()
                val isSendToUserEmail = isSendUserEmailCheckbox.isChecked
                viewModel.sendReportToEmail(receiver, isSendToUserEmail)
            }
            cancelTextView.setOnClickListener {
                //viewModel.close()
            }
        }
        viewModel.observeSendReportToEmail(this)
    }

}