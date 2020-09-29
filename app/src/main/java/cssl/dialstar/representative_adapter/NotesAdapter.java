package cssl.dialstar.representative_adapter;

/**
 * Created by ravi on 20/02/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_fragment.Discussion;
import cssl.dialstar.representative_fragment.EventDetails;
import cssl.dialstar.representative_fragment.Grievance_desc;
import cssl.dialstar.representative_util.Note;
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_fragment.EventDesc;
import cssl.dialstar.user_fragment.GrievanceDetails;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    SharedPreferences share;

    String usertype="";

    private Context context;
    private List<Note> notesList;
    android.support.v4.app.Fragment fragment;
    PopupWindow popupWindow;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView note;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            note = view.findViewById(R.id.note);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);




        }
    }


    public NotesAdapter(Context context, List<Note> notesList, PopupWindow popupWindow) {
        this.context = context;
        this.notesList = notesList;
        this.popupWindow = popupWindow;
        share = PreferenceManager.getDefaultSharedPreferences(context);
        usertype=share.getString("usertype","");

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);


        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Note note = notesList.get(position);

        if(note.getNote()!=null)
            holder.note.setText(note.getNote());
        else if(note.getEvent()!=null)
            holder.note.setText(note.getEvent());
        else if(note.getDiscussion()!=null)
            holder.note.setText(note.getDiscussion());
        else{}



        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.timestamp.setText(formatDate(note.getTimestamp()));

        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                  /*      Toast.makeText(context, "Grievance id "+notesList.get(position).getGrievanceid()+"",
                                Toast.LENGTH_SHORT).show();*/




                        int grievanceid=0;
                        int eventid =0;
                        int discussionid =0;
                        grievanceid = notesList.get(position).getGrievanceid();
                         eventid = notesList.get(position).getEventid();
                        discussionid = notesList.get(position).getDiscussionid();

                        if(grievanceid>0){
                            if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
                                fragment = Grievance_desc.newInstance(grievanceid);
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    popupWindow.dismiss();

                                }
                            }
                            else if(usertype.equalsIgnoreCase("representative")){
                                fragment = Grievance_desc.newInstance(grievanceid);
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                    popupWindow.dismiss();
                                }
                            }
                            else if(usertype.equalsIgnoreCase("user")){
                                fragment = GrievanceDetails.newInstance(grievanceid);
                                UserDashboard.setBlankTab();
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((UserDashboard)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    popupWindow.dismiss();

                                }
                            }



                        }else if(eventid>0){

                            if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
                                fragment = EventDetails.newInstance(eventid);
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    popupWindow.dismiss();

                                }
                            }
                            else if(usertype.equalsIgnoreCase("representative")){
                                fragment = EventDetails.newInstance(eventid);
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                    popupWindow.dismiss();
                                }
                            }
                            else if(usertype.equalsIgnoreCase("user")){
                                fragment = EventDesc.newInstance(eventid);

                                UserDashboard.setEventTabposition();
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((UserDashboard)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    popupWindow.dismiss();

                                }
                            }




                        }else if(discussionid>0){

                            if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
                                fragment = new Discussion();
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    popupWindow.dismiss();

                                }
                            }
                            else if(usertype.equalsIgnoreCase("representative")){
                                fragment = new Discussion();
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();

                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();

                                    popupWindow.dismiss();
                                }
                            }
                            else if(usertype.equalsIgnoreCase("user")){
                                UserDashboard.setDiscussionTabposition();
                                fragment = new cssl.dialstar.user_fragment.Discussion();
                                if (fragment != null) {
                                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                                    FragmentManager fragmentManager = ((UserDashboard)context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    popupWindow.dismiss();

                                }
                            }




                        }




                    }
                }
        );



    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {

            try {

                //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               // SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
                SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy, hh:mm:ss a");
                Date date = fmt.parse(dateStr);


                SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
                return fmtOut.format(date);
            } catch (ParseException e) {

            }

        return dateStr;
    }

}
