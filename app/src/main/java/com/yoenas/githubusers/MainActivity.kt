package com.yoenas.githubusers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var listUser: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addItemUsersFromJSON()

        binding.rvUser.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = UserAdapter(listUser)
        }
    }

    private fun addItemUsersFromJSON() {
        try {
            val jsonObject = JSONTokener(readDataFromFileJSON()).nextValue() as JSONObject
            Log.i("addItemUser", "addItemUsersFromJSON: $jsonObject")
            val jsonArray = jsonObject.getJSONArray("users")
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val username = itemObj.getString("username")
                val name = itemObj.getString("name")
                val avatar = itemObj.getString("avatar")
                val company = itemObj.getString("company")
                val location = itemObj.getString("location")
                val repository = itemObj.getInt("repository")
                val follower = itemObj.getInt("follower")
                val following = itemObj.getInt("following")
                val user =
                    User(username, name, avatar, company, location, repository, follower, following)
                listUser.add(user)
            }
        } catch (e: JSONException) {
            Log.d("MainActivity", "addItemsFromJSON: ", e)
        } catch (e: IOException) {
            Log.d("MainActivity", "addItemsFromJSON: ", e)
        }
    }

    @Throws(IOException::class)
    private fun readDataFromFileJSON(): String {
        val builder = StringBuilder()
        var inputStream: InputStream? = null

        try {
            var jsonString: String?
            inputStream = resources.openRawResource(R.raw.githubuser)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }
}





