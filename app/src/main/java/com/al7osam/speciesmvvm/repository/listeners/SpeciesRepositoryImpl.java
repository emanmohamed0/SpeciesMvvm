package com.al7osam.speciesmvvm.repository.listeners;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.al7osam.speciesmvvm.repository.api.AppServices;
import com.al7osam.speciesmvvm.model.Species;
import com.al7osam.speciesmvvm.model.SpeciesList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eman.eraqi on 12/30/2018.
 */

public class SpeciesRepositoryImpl  {
    AppServices appServices;
    SpeciesRepository speciesRepository;

    public SpeciesRepositoryImpl(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
        this.appServices = new AppServices();
    }

    public LiveData<List<Species>> getAllSpecies() {
        final MutableLiveData<List<Species>> data = new MutableLiveData<>();
        appServices.getApi().getSpecies().enqueue(new Callback<SpeciesList>() {
            @Override
            public void onResponse(Call<SpeciesList> call, Response<SpeciesList> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body().getSpeciesList());
                    speciesRepository.getSpecies(data);
                }
            }

            @Override
            public void onFailure(Call<SpeciesList> call, Throwable t) {
                data.setValue(null);
                speciesRepository.onError(t);
            }
        });


//        appServices.getApi().getAllSpecies().


        return data;
    }


}
