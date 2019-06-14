package com.aac.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class CohortsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cohorts, container, false);



        ListView listView = (ListView) view.findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);


        return view;
    }


    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            int size = 75;

            return size;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            final String[] cohort_list = {
                    "Cohort 1",
                    "Yasmin Salemi",
                    "Easa Ahmadzai",
                    "Cohort 2",
                    "Nasira Rajabi",
                    "Weiss Hamid",
                    "Cohort 3",
                    "Tarina Ayazi",
                    "Sajad Ghanizada",
                    "Cohort 4",
                    "Mursal Noory",
                    "Yosef Mahmood",
                    "Cohort 5",
                    "Omida Shahab",
                    "Omed Amin",
                    "Cohort 6",
                    "Shabir Sarwary",
                    "Heelai Ahmadullah",
                    "Cohort 7",
                    "Abrahim Hussain",
                    "Salima Sekandari",
                    "Cohort 8",
                    "Mujtaba Shahsamand",
                    "Neelab Naibkhyl",
                    "Cohort 9",
                    "Kane Dabir",
                    "Arezo Mayel",
                    "Cohort 10",
                    "Morcel Hamidy",
                    "Omeed Farani",
                    "Cohort 11",
                    "Sabrina Nassir",
                    "Murtaza Aliyar",
                    "Cohort 12",
                    "Framerz Jaghori",
                    "Tamana Noory",
                    "Cohort 13",
                    "Ebadullah Ebadi",
                    "Nadia Ramin",
                    "Cohort 14",
                    "Ali Zekeria",
                    "Mishal Pahrand",
                    "Cohort 15",
                    "Hamed Ahmadzai",
                    "Mariam Mahbob",
                    "Cohort 16",
                    "Aziz Sadat",
                    "Ariana Piloti",
                    "Cohort 17",
                    "Mouzima Mousumi",
                    "Shahpour Ismail",
                    "Cohort 18",
                    "Rafiullah Hamedy",
                    "Zareena Sultani",
                    "Cohort 19",
                    "Darya Moini",
                    "Mustafa Salemi",
                    "Cohort 20",
                    "Hussai Nuristani",
                    "Imron Saddozai",
                    "Cohort 21",
                    "Yoseph Raja",
                    "Rana Ahmadi",
                    "Cohort 22",
                    "Asseelah Azimi",
                    "Suliman Razai",
                    "Cohort 23",
                    "Farishta Nawaby",
                    "Mokhtar Kazemi",
                    "Cohort 24",
                    "Wally Omar",
                    "Daywa Ahmadi",
                    "Cohort 25",
                    "Habib Sahar",
                    "Sayeda Akbary"
            };





            if (position % 3 == 0) {
                view = getLayoutInflater().inflate(R.layout.cohort_sectionheader, null);
                view.setBackgroundResource(R.color.lists);


                TextView textViewCohortNumber = (TextView) view.findViewById(R.id.cohortheader);
                textViewCohortNumber.setText(cohort_list[position]);


            }

            else {

                view = getLayoutInflater().inflate(R.layout.person_item, null);
                view.setBackgroundResource(R.color.lists);

                TextView txtTitle = (TextView) view.findViewById(R.id.item);
                ImageView imageView = (ImageView) view.findViewById(R.id.icon);



                Integer[] cohort_images = {
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.easa_ahmadzai,
                        R.drawable.no_image,
                        R.drawable.nasira_rajabi,
                        R.drawable.weiss_hamid,
                        R.drawable.no_image,
                        R.drawable.tarina_ayazi,
                        R.drawable.sajad_ghanizada,
                        R.drawable.no_image,
                        R.drawable.mursal_noory,
                        R.drawable.yosef_mahmood,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.omed_amin,
                        R.drawable.no_image,
                        R.drawable.shabir_sarwary,
                        R.drawable.heelai_ahmadullah,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.salima_sekandari,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.kane_dabir,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.morcel_hamidy,
                        R.drawable.omeed_farani,
                        R.drawable.no_image,
                        R.drawable.sabrina_nassir,
                        R.drawable.murtaza_aliyar,
                        R.drawable.no_image,
                        R.drawable.framerz_jaghori,
                        R.drawable.tamana_noory,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.nadia_ramin,
                        R.drawable.no_image,
                        R.drawable.ali_zekeria,
                        R.drawable.mishal_pahrand,
                        R.drawable.no_image,
                        R.drawable.hamed_ahmadzai,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.aziz_sadat,
                        R.drawable.ariana_piloti,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.shahpour_ismail,
                        R.drawable.no_image,
                        R.drawable.rafiullah_hamedy,
                        R.drawable.zareena_sultani,
                        R.drawable.no_image,
                        R.drawable.darya_moini,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.hussai_nuristani,
                        R.drawable.imron_saddozai,
                        R.drawable.no_image,
                        R.drawable.yoseph_raja,
                        R.drawable.rana_ahmadi,
                        R.drawable.no_image,
                        R.drawable.asseelah_azimi,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.farishta_nawaby,
                        R.drawable.no_image,
                        R.drawable.no_image,
                        R.drawable.wally_omar,
                        R.drawable.daywa_ahmadi,
                        R.drawable.no_image,
                        R.drawable.habib_sahar,
                        R.drawable.sayeda_akbary
                };

                txtTitle.setText(cohort_list[position]);
                imageView.setImageResource(cohort_images[position]);



            }



            return view;
        }
    }
}