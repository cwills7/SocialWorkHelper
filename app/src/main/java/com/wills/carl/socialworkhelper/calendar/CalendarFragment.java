package com.wills.carl.socialworkhelper.calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wills.carl.socialworkhelper.R;
import com.wills.carl.socialworkhelper.Supervision;
import com.wills.carl.socialworkhelper.db.EventDb;
import com.wills.carl.socialworkhelper.edit.EditEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    final String testAddAppID= "ca-app-pub-3940256099942544/6300978111";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView mTextView;
    TextView mMonthName;
    TextView mPreviewText;
    Button mEditButton;
    EditText mMonthNote;
    CompactCalendarView compactCalendarView = null;
    private AdView adView;

    private OnFragmentInteractionListener mListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        compactCalendarView = (CompactCalendarView) rootView.findViewById(R.id.cv_calendar);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        final EventDb db = new EventDb(rootView.getContext());
        List<Supervision> events = db.getEventsFromDB();

        for(Supervision sup: events){
            Event event = sup.convertToEvent();
            compactCalendarView.addEvent(event, false);
        }

        mTextView = (TextView) rootView.findViewById(R.id.my_text_view);
        mMonthName = (TextView) rootView.findViewById(R.id.tv_month_name);
        mMonthName.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        mPreviewText = (TextView) rootView.findViewById(R.id.tv_preview_text);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                mPreviewText.setText("");
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                for (Event event : events){
                    mPreviewText.append(event.getData() + "\n---------------------\n");
                }
                mTextView.setText(Long.toString(dateClicked.getTime()));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                //db.insertMonthNote(convertToMonthInt(mMonthName.getText().toString()), convertToYearInt(mMonthName.getText().toString()), mMonthNote.getText().toString());
                mMonthName.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
                //mMonthNote.setText(db.getMonthNote(convertToMonthInt(mMonthName.getText().toString()), convertToYearInt(mMonthName.getText().toString())));
            }
        });

        mEditButton = (Button) rootView.findViewById(R.id.calendar_edit_button);
        mEditButton.setText("EDIT");
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), EditEvent.class);
                Log.d("CALENDER FRAG", "Text: " + mTextView.getText());

                intent.putExtra("dateInMillis", mTextView.getText());
                startActivityForResult(intent,1);
            }
        });

       /* mMonthNote = (EditText) rootView.findViewById(R.id.et_month_note);
        mMonthNote.setCursorVisible(false);
        mMonthName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus){
                }
            }
        });
*/
        Event ev1 = new Event(Color.GREEN, 1515974400000L, "Some Text to Store");
        Event ev2 = new Event(Color.GREEN, 1516168800000L, "some text for another day");
        Event ev3 = new Event(Color.GREEN, 1515974400000L, "Some MORE Text to Store");

        compactCalendarView.addEvent(ev1, false);
        compactCalendarView.addEvent(ev2, false);
        compactCalendarView.addEvent(ev3, false);

        MobileAds.initialize(rootView.getContext(), testAddAppID);

        adView = rootView.findViewById(R.id.addBar);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1) {
            if (data != null) {
                compactCalendarView.addEvent(new Event(Color.GREEN,
                        data.getLongExtra("date", System.currentTimeMillis()), data.getStringExtra("data")), false);
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onFragmentInteraction(Uri uri);
    }

    private int convertToMonthInt(String monthYear){
        switch(monthYear.substring(0,3).toUpperCase()){
            case "JAN": return 1;
            case "FEB": return 2;
            case "MAR": return 3;
            case "APR": return 4;
            case "MAY": return 5;
            case "JUN": return 6;
            case "JUL": return 7;
            case "AUG": return 8;
            case "SEP": return 9;
            case "OCT": return 10;
            case "NOV": return 11;
            case "DEC": return 12;
            default: return 0;
        }
    }

    private int convertToYearInt(String monthYear){
        String year = monthYear.substring(6);
        return Integer.parseInt(year);
    }


}
