package pt.isel.pdm.chess4android.offline.game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import pt.isel.pdm.chess4android.R
import pt.isel.pdm.chess4android.activities.CreditsActivity
import pt.isel.pdm.chess4android.databinding.ActivityOfflineBinding
import pt.isel.pdm.chess4android.offline.pieces.Team
import pt.isel.pdm.chess4android.offline.puzzle.myPostDelayed
import pt.isel.pdm.chess4android.views.OnTileTouchListener
import pt.isel.pdm.chess4android.views.Type

/**
 * Activity for the offline game
 */
class OfflineActivity : AppCompatActivity() {

    private val binding by lazy { ActivityOfflineBinding.inflate(layoutInflater) }

    private val viewModel: OfflineViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.finishPhrase.visibility = View.INVISIBLE

        viewModel.offlineBoardData.observe(this) {
            binding.board.drawBoard(it.board)
        }

        viewModel.paint.observe(this) {
            it?.list?.forEach { pos ->
                binding.board.drawSelected(pos, it.type)
            }
        }

        viewModel.xequeMate.observe(this) { result ->
            result.pieces.forEach { pieces ->
                pieces.forEach {
                    binding.board.drawSelected(
                        it.location, Type.XEQUE
                    )
                }
            }
            binding.board.isEnabled = false
            binding.finishPhrase.visibility = View.VISIBLE
            binding.finishPhrase.text =
                if (result.team == Team.BLACK) resources.getString(R.string.black_wins)
                else resources.getString(R.string.white_wins)
            binding.root.myPostDelayed(10000) {
                startActivity(Intent(this, CreditsActivity::class.java))
            }
        }

        binding.forfeit.setOnClickListener {
            viewModel.forfeit()
        }

        viewModel.promotion.observe(this) {
            promotionOptions()
        }

        binding.board.setListener(object : OnTileTouchListener {
            override fun onClick(x: Int, y: Int): Boolean {
                viewModel.movePiece(x, y)
                return true
            }
        })
    }

    @SuppressLint("StringFormatMatches")
    private fun promotionOptions() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.promotion))
            .setItems(R.array.promote_items) { _, which ->
                viewModel.promote(
                    resources.getStringArray(R.array.promote_items)[which]
                )
            }
            .create()
            .show()
    }
}