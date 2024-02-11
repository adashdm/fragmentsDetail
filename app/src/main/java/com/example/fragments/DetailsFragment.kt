import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fragments.ApiClient
import com.example.lists.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {

    private lateinit var detailsTitle: TextView
    private lateinit var heroImageView: ImageView
    private lateinit var powerstatsTextView: TextView
    private lateinit var appearanceTextView: TextView
    private lateinit var biographyTextView: TextView
    private lateinit var workTextView: TextView
    private lateinit var connectionsTextView: TextView
    private lateinit var selectedHeroName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsTitle = view.findViewById(R.id.detailsTitle)
        heroImageView = view.findViewById(R.id.heroImageView)
        powerstatsTextView = view.findViewById(R.id.powerstatsTextView)
        appearanceTextView = view.findViewById(R.id.appearanceTextView)
        biographyTextView = view.findViewById(R.id.biographyTextView)
        workTextView = view.findViewById(R.id.workTextView)
        connectionsTextView = view.findViewById(R.id.connectionsTextView)

        arguments?.let {
            selectedHeroName = it.getString(SELECTED_HERO) ?: ""
            detailsTitle.text = selectedHeroName

            ApiClient.client.getHeroes().enqueue(object : Callback<List<Hero>> {
                override fun onResponse(call: Call<List<Hero>>, response: Response<List<Hero>>) {
                    if (response.isSuccessful) {
                        val hero = response.body()?.find { it.name == selectedHeroName }
                        hero?.let {
                            Glide.with(requireContext())
                                .load(it.images.sm)
                                .into(heroImageView)

                            val powerstatsText =
                                    "Intelligence: ${it.powerstats.intelligence}\n" +
                                    "Strength: ${it.powerstats.strength}\n" +
                                    "Speed: ${it.powerstats.speed}\n" +
                                    "Durability: ${it.powerstats.durability}\n" +
                                    "Power: ${it.powerstats.power}\n" +
                                    "Combat: ${it.powerstats.combat}"
                            powerstatsTextView.text = powerstatsText

                            val appearanceText =
                                    "Gender: ${it.appearance.gender}\n" +
                                    "Race: ${it.appearance.race}\n" +
                                    "Height: ${it.appearance.height.joinToString()}\n" +
                                    "Weight: ${it.appearance.weight.joinToString()}\n" +
                                    "Eye Color: ${it.appearance.eyeColor}\n" +
                                    "Hair Color: ${it.appearance.hairColor}"
                            appearanceTextView.text = appearanceText

                            val biographyText =
                                    "Full Name: ${it.biography.fullName}\n" +
                                    "Alter Egos: ${it.biography.alterEgos}\n" +
                                    "Aliases: ${it.biography.aliases.joinToString()}\n" +
                                    "Place of Birth: ${it.biography.placeOfBirth}\n" +
                                    "First Appearance: ${it.biography.firstAppearance}\n" +
                                    "Publisher: ${it.biography.publisher}\n" +
                                    "Alignment: ${it.biography.alignment}"
                            biographyTextView.text = biographyText

                            val workText =
                                    "Occupation: ${it.work.occupation}\n" +
                                    "Base: ${it.work.base}"
                            workTextView.text = workText

                            val connectionsText =
                                    "Group Affiliation: ${it.connections.groupAffiliation}\n" +
                                    "Relatives: ${it.connections.relatives}"
                            connectionsTextView.text = connectionsText
                        }
                    }
                }

                override fun onFailure(call: Call<List<Hero>>, t: Throwable) {
                }
            })
        }
    }

    companion object {
        private const val SELECTED_HERO = "selected_hero"

        fun newInstance(heroName: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString(SELECTED_HERO, heroName)
            fragment.arguments = args
            return fragment
        }
    }
}
