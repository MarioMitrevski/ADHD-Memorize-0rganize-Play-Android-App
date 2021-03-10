package com.example.myfirstapp.ui.content.relief;

import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.R;
import com.example.myfirstapp.ui.content.relief.network.VideoItem;
import com.example.myfirstapp.ui.content.relief.network.VideoItemsCollection;

import java.util.ArrayList;
import java.util.List;


public class VideoViewModel extends ViewModel {

    VideoItemsCollection videoItemsCollection;

    public void init() {
        videoItemsCollection = new VideoItemsCollection();
        List<VideoItem> videos = new ArrayList<>();

        videos.add(new VideoItem("Што е АДХД?", "4:28", R.drawable.video1, "https://www.youtube.com/watch?v=5l2RIOhDXvU"));
        videos.add(new VideoItem("Ајде да зборуваме за АДХД", "4:11", R.drawable.video2, "https://www.youtube.com/watch?v=YeamHE6Kank"));
        videos.add(new VideoItem("АДХД и здрава исхрана", "3:17", R.drawable.video3, "http://www.youtube.com/watch?v=36KQrfRSLzY"));
        videos.add(new VideoItem("5 интересни факти за АДХД?", "4:32", R.drawable.video4, "http://www.youtube.com/watch?v=uW6e50NYlWE"));
        videos.add(new VideoItem("АДХД кај девојчиња", "4:52", R.drawable.video5, "http://www.youtube.com/watch?v=dmeE3qTJRUw"));
        videos.add(new VideoItem("Детство со АДХД?", "4:34", R.drawable.video6, "https://www.youtube.com/watch?v=N2-nnHGlPbc"));
        videos.add(new VideoItem("АДХД интензивно олеснување, АДХД фокус музика, АДХД музичка терапија, изохронични тонови", "3:00:00", R.drawable.video7, "https://www.youtube.com/watch?v=jvM9AfAzoSo"));
        videos.add(new VideoItem("Олеснување на АДХД - Зголемете го фокусот / концентрацијата / меморијата - Бинаурални ритмови - Фокус музика", "1:30:00", R.drawable.video8, "https://www.youtube.com/watch?v=CMnIsnINckU"));
        videos.add(new VideoItem("Фокус музика за подобра концентрација, Музика за учење", "3:01:26", R.drawable.video9, "https://www.youtube.com/watch?v=kGhHPX_TaI0"));
        videos.add(new VideoItem("Музика за учење за интензивно олеснување на АДХД со изохронични тонови - амбиентален поток", "3:00:00", R.drawable.video10, "https://www.youtube.com/watch?v=0J3BNYXXAA0"));

        videoItemsCollection.setVideoItems(videos);
    }

    public List<VideoItem> getVideoItems() {
        return videoItemsCollection.getVideoItems();
    }
}
