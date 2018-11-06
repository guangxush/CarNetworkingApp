package com.example.chelianwang.adapter;


        import java.util.List;

        import android.R.color;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.example.chelianwang.R;
        import com.example.chelianwang.model.Music;

public class MusicAdapter extends BaseAdapter {

    List<Music> musicList;
    int playPosition = -1;

    @Override
    public int getCount() {
        if(musicList == null)
            return 0;
        else return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;
        if(view == null) {
            holder = new Holder();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musiclist, null);
            holder.txtName = (TextView) view.findViewById(R.id.txt_musicname);
            holder.txtSize = (TextView) view.findViewById(R.id.txt_musicsize);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.txtName.setText(musicList.get(position).getName());
        holder.txtSize.setText(getStrTime(musicList.get(position).getDuration()));

        if(playPosition == position) {
            view.setBackgroundResource(R.drawable.cur);
        } else {
            view.setBackgroundColor(color.transparent);
        }

        return view;
    }

    class Holder {
        TextView txtName, txtSize;
    }

    private String getStrTime(long dura) {
        long duration = dura;
        int minute = (int) (duration / 1000 /60);
        int sec = (int) ((duration / 1000) - minute * 60);
        return minute + ":" +sec;
    }

    public void update(List<Music> musicList) {
        this.musicList = musicList;
        notifyDataSetChanged();
    }

    public void playView(int playPosition) {
        this.playPosition = playPosition;
        notifyDataSetChanged();
    }



}
