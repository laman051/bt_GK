package com.example.anew

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    internal  var dbHelper = DatabaseHelper(this)
    fun showToast(text:String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }
    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }
    fun clearEditTexts(){
        nameTxt.setText("")
        ageTxt.setText("")
        genderTxt.setText("")
        idTxt.setText("")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleInserts()
        handleUpdates()
        handleDeletes()
        handleViewing()
    }
    fun handleInserts() {
        insertBtn.setOnClickListener {
            try {
                dbHelper.insertData(nameTxt.text.toString(),ageTxt.text.toString(),
                    genderTxt.text.toString())
                clearEditTexts()
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
    fun handleUpdates() {
        updateBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(
                    idTxt.text.toString(),
                    nameTxt.text.toString(),
                    ageTxt.text.toString(),
                    genderTxt.text.toString())
                if (isUpdate == true)
                    showToast("Sửa thành công")
                else
                    showToast("Sửa thất bại")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
    fun handleDeletes(){
        deleteBtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                clearEditTexts()
                showToast("Xóa thành công")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
    /**
     * When our View All is clicked
     */
    fun handleViewing() {
        viewBtn.setOnClickListener(
            View.OnClickListener {
                val res = dbHelper.allData
                if (res.count == 0) {
                    showDialog("Lỗi", "Không tìm thấy dữ liệu")
                    return@OnClickListener
                }
                val buffer = StringBuffer()
                while (res.moveToNext()) {
                    buffer.append("ID :" + res.getString(0) + "\n")
                    buffer.append("NAME :" + res.getString(1) + "\n")
                    buffer.append("AGE :" + res.getString(2) + "\n")
                    buffer.append("GENDER :" + res.getString(3) + "\n\n")
                }
                showDialog("Danh sách", buffer.toString())
            }
        )
    }
}