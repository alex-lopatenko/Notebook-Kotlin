package android.example.sqlitekotlin

import android.app.Activity
import android.content.Intent
import android.example.sqlitekotlin.db.MyDbManager
import android.example.sqlitekotlin.db.MyIntentConstants
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.constraintlayout.solver.state.State
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {

    var id = 0
    var isEditState = false
    val imageRequestCode = 10
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getMyIntents()
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
            contentResolver.takePersistableUriPermission(
                data?.data!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
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
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    fun onClickSave(view: View) {
        val myTitle = edTitle.text.toString()
        val myDesc = edDesc.text.toString()

        if (myTitle != "" && myDesc != "") {
            if (isEditState) {
                myDbManager.updateItem(myTitle, myDesc, tempImageUri, id)
            } else {
                myDbManager.insertToDb(myTitle, myDesc, tempImageUri)
            }
            finish()
        }
    }

    fun onEditEnable(view: View){
        edTitle.isEnabled = true
        edDesc.isEnabled = true
        fbEdit.visibility = View.GONE
    }

    fun getMyIntents() {
        fbEdit.visibility = View.GONE
        val i = intent

        if (i != null) {

            if (i.getStringExtra(MyIntentConstants.I_TITLE_KEY) != null) {

                fbAddImage.visibility = View.GONE
                edTitle.setText(i.getStringExtra(MyIntentConstants.I_TITLE_KEY))
                isEditState = true
                edTitle.isEnabled = false
                edDesc.isEnabled = false
                fbEdit.visibility = View.VISIBLE
                edDesc.setText(i.getStringExtra(MyIntentConstants.I_DESC_KEY))
                id = i.getIntExtra(MyIntentConstants.I_ID_KEY, 0)
                if (i.getStringExtra(MyIntentConstants.I_URI_KEY) != "empty") {

                    mainImageLayout.visibility = View.VISIBLE
                    imMainImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstants.I_URI_KEY)))
                    imButtonDeleteImage.visibility = View.GONE
                    imButtonEditImage.visibility = View.GONE
                }
            }
        }
    }
}