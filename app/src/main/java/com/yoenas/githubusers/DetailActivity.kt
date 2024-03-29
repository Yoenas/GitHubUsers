package com.yoenas.githubusers

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.yoenas.githubusers.databinding.ActivityDetailBinding
import java.io.File
import java.io.FileOutputStream

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var dataUser: User? = null

    companion object {
        const val EXTRA_DATA_USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.txt_title_detail)

        dataUser = intent.parcelable(EXTRA_DATA_USER)

        initView()
    }

    @SuppressLint("DiscouragedApi")
    private fun initView() {
        // get image resource id
        val imageResource: Int = resources.getIdentifier(dataUser?.avatar, null, packageName)

        binding.apply {
            Glide.with(applicationContext).load(imageResource).into(imgAvatarDetail)
            tvNameDetail.text = dataUser?.name
            tvUsernameDetail.text = dataUser?.username
            tvCompanyDetail.text = dataUser?.company
            tvLocationDetail.text = dataUser?.location
            tvRepositaryDetail.text = dataUser?.repository.toString()
            tvFollowersDetail.text = dataUser?.follower.toString()
            tvFollowingDetail.text = dataUser?.following.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("DiscouragedApi")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu_share -> {

                val textValue = "This is ${dataUser?.name}'s GitHub Profile\n" +
                        "Username: ${dataUser?.username}\n" +
                        "Company: ${dataUser?.company}\n" +
                        "Location: ${dataUser?.location}\n" +
                        "Total ${dataUser?.repository} Repositories\n" +
                        "${dataUser?.follower} Followers & ${dataUser?.following} Following"

                val imageResource: Int =
                    resources.getIdentifier(dataUser?.avatar, null, packageName)
                val bitmap = ResourcesCompat.getDrawable(resources, imageResource, null)?.toBitmap()
                val outputFile = File(cacheDir, "${dataUser?.name}.png")
                val outputStream = FileOutputStream(outputFile)
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textValue)
                    putExtra(
                        Intent.EXTRA_STREAM,
                        if (SDK_INT >= VERSION_CODES.N) androidx.core.content.FileProvider.getUriForFile(
                            this@DetailActivity,
                            packageName,
                            outputFile
                        )
                        else Uri.fromFile(outputFile)
                    )
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, getString(R.string.txt_share_to))
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }
}