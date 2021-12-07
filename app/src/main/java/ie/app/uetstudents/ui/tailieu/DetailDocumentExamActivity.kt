package ie.app.uetstudents.ui.tailieu

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.load.model.FileLoader
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.Entity.subject.DataSubject.ExamDocumentDto
import kotlinx.android.synthetic.main.activity_detail_document_exam.*

class DetailDocumentExamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_document_exam)
        back_document_exam.setOnClickListener {
            onBackPressed()
        }
        val link = intent.getStringExtra("ExamDocument")
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(link)
        progress_bar.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack()
        }
        else
        {
            super.onBackPressed()
        }

    }
}