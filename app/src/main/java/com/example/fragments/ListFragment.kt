import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.fragments.ApiClient
import com.example.lists.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var heroes: List<Hero>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listView = view.findViewById(R.id.listView)

        ApiClient.client.getHeroes().enqueue(object : Callback<List<Hero>> {
            override fun onResponse(call: Call<List<Hero>>, response: Response<List<Hero>>) {
                if (response.isSuccessful) {
                    heroes = response.body() ?: emptyList()
                    val heroNames = heroes.map { it.name }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, heroNames)
                    listView.adapter = adapter

                }
            }

            override fun onFailure(call: Call<List<Hero>>, t: Throwable) {
            }
        })


        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedHeroName = parent.getItemAtPosition(position) as String
            val detailsFragment = DetailsFragment.newInstance(selectedHeroName)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detailsFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
