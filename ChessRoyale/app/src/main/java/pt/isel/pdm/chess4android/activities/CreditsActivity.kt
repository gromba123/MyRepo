package pt.isel.pdm.chess4android.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.databinding.ActivityCreditsBinding

private const val GitHubURL = "https://lichess.org/api"

/**
 * Represents the credits screen
 */
class CreditsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreditsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.lichessIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GitHubURL)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }
}