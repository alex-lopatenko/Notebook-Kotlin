package android.example.sqlitekotlin

import android.app.Activity
import android.content.Intent
import android.example.sqlitekotlin.db.MyDbManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.solver.state.State

class EditActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val imageRequestCode = 10
    var tempImageUri = "empty"

    var mainImageLayout = findViewById<ImageView>(R.id.myImageLayout)
    var fbAddImage = findViewById<ImageView>(R.id.fbAddImage)
    var imMainImage = findViewById<ImageView>(R.id.imMainImage)
    var edTitle = findViewById<EditText>(R.id.edTitle)
    var edDesc = findViewById<EditText>(R.id.edDesc)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK && requestCode == imageRequestCode) {
            imMainImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
        }

    }

    fun onClickAddImage(view: View) {
        mainImageLayout.visibility = View.VISIBLE
        fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        mainImageLayout.visibility = View.GONE
        fbAddImage.visibility = View.VISIBLE
    }

    fun onClickChooseImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    fun onClickSave(view: View) {
       val myTitle = edTitle.text.toString()
       val myDesc = edDesc.text.toString()
        if (myTitle != "" && myDesc != "") {
           myDbManager.insertToDb(myTitle, myDesc, tempImageUri)
        }
    }
}