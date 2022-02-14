package pt.isel.pdm.chess4android.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.databinding.ActivityLaunchBinding
import pt.isel.pdm.chess4android.offline.game.OfflineActivity
import pt.isel.pdm.chess4android.offline.history.HistoryActivity
import pt.isel.pdm.chess4android.online.challenges.list.ChallengesListActivity

/**
 * Represents the launching activity
 */
class LaunchActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLaunchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.offline.setOnClickListener {
            startActivity(Intent(this, OfflineActivity::class.java))
        }

        binding.online.setOnClickListener {
            startActivity(Intent(this, ChallengesListActivity::class.java))
        }

        binding.fetchButton.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.creditsButton.setOnClickListener {
            startActivity(Intent(this, CreditsActivity::class.java))
        }
    }
}