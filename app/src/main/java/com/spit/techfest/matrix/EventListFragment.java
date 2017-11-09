package com.spit.techfest.matrix;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.spit.techfest.matrix.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link //Callbacks}
 * interface.
 *
 * param1 -
 *  type of ordering
 *  - time-sorted, repeated events
 *  - favorites
 *  - category-wise
 *
 * param2 -
 *  category
 */
public class EventListFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String SORT_FAVORITE = "favorite";
    public static final String SORT_CATEGORY = "category";
    public static final String SORT_TIME = "schedule";

    public static List<EventItem> mEvents;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private static JSONArray jsonArray;
    //private static List<EventItem> l = new ArrayList<EventItem>();


    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }









        List<EventItem> arrayList = new ArrayList();

        if(mParam1.equals(SORT_CATEGORY))
            arrayList = getByCategory(mParam2);
        else if(mParam1.equals(SORT_FAVORITE))
            arrayList = getFavoriteEvents();
        else if(mParam1.equals(SORT_TIME))
            arrayList = getSorted();


        // TODO: Change Adapter to display your content
        mAdapter = new EventListAdapter(getActivity(),R.layout.event_item, arrayList);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventlist, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            //   throw new ClassCastException(activity.toString()
            //       + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //  mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    private List<EventItem> getFavoriteEvents() {
        List<EventItem> favoriteEvents = new ArrayList<EventItem>();

        for(EventItem event : mEvents){
            if(event.getFavorite()) favoriteEvents.add(event);
        }

        return favoriteEvents;
    }

    private List<EventItem> getByCategory(CharSequence category) {
        List<EventItem> categoryEvents = new ArrayList<EventItem>();

        for(EventItem event : mEvents){
            if(event.getCategory().equalsIgnoreCase(category.toString())) categoryEvents.add(event);
        }

        return categoryEvents;
    }

    private List<EventItem> getSorted() {
        Collections.sort(mEvents, new Comparator<EventItem>() {
            @Override
            public int compare(EventItem eventItem, EventItem eventItem2) {
                int[] eventTime = {Integer.parseInt(eventItem.getTimeDay1().split(":")[0]),
                        Integer.parseInt(eventItem.getTimeDay1().split(":")[1])};
                int[] eventTime2 = {Integer.parseInt(eventItem2.getTimeDay1().split(":")[0]),
                        Integer.parseInt(eventItem2.getTimeDay1().split(":")[1])};

                if (eventTime[0] > eventTime2[0])
                    return 1;

                else if (eventTime[0] == eventTime2[0])
                    if (eventTime[1] == eventTime2[1])
                        return 0;
                    else if (eventTime[1] > eventTime2[1])
                        return 1;
                    else return -1;

                return -1;
            }
        });

        return mEvents;
    }
}
