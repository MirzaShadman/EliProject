package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.demo.eliproject.R
import viewmodel.MainSharedViewModel

class OrderSuccesFragment: Fragment() {
    private lateinit var model: MainSharedViewModel
    
    private lateinit var button: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(MainSharedViewModel::class.java)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.order_success_layout, container,false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button = view.findViewById(R.id.continue_shopping)
        
        button.setOnClickListener {
            model.clearProduct()
            Navigation.findNavController(button).navigate(R.id.action_orderSuccesFragment2_to_productFragment)
        }
    }
    
    
    
    companion object {
        fun newInstance(): OrderSuccesFragment {
            return OrderSuccesFragment()
        }
    }
}