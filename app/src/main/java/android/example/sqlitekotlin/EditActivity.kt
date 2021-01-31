package android.example.sqlitekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.solver.state.State

class EditActivity : AppCompatActivity() {
    var mainImageLayout = findViewById<View>(R.id.myImageLayout)
    var fbAddImage = findViewById<View>(R.id.fbAddImage)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
    }

    fun onClickAddImage(view: View) {
        mainImageLayout.visibility = View.VISIBLE
        fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        mainImageLayout.visibility = View.GONE
        fbAddImage.visibility = View.VISIBLE
    }
}