package figuitosinc.pocketchef.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class IngredientFilterViewModel extends AndroidViewModel {

    private IngredientDao ingredientDao;
    private MediatorLiveData<List<IngredientEntity>> unselectedIngredients;
    private MutableLiveData<List<IngredientEntity>> selectedIngredients;
    private LiveData<List<IngredientEntity>> allIngredients;
    private List<IngredientEntity> allIngredientsList = new ArrayList<>();


    public IngredientFilterViewModel(@NonNull Application application) {
        super(application);
        this.ingredientDao = RecipeIngredientDatabase.getInstance(application).ingredienteDao();
        this.allIngredients = ingredientDao.findAllIngredients();
        this.unselectedIngredients = new MediatorLiveData<>();
        unselectedIngredients.setValue(new ArrayList<IngredientEntity>());

        this.selectedIngredients = new MutableLiveData<>();
        selectedIngredients.setValue(new ArrayList<IngredientEntity>());

        unselectedIngredients.addSource(allIngredients, new Observer<List<IngredientEntity>>() {

            @Override
            public void onChanged(@Nullable List<IngredientEntity> ingredientEntities) {
                Log.d("ViewModel", "A");
                allIngredientsList = ingredientEntities;
                mergeLists();
            }
        });

        unselectedIngredients.addSource(selectedIngredients, new Observer<List<IngredientEntity>>() {
            @Override
            public void onChanged(@Nullable List<IngredientEntity> ingredientEntities) {
                Log.d("ViewModel", "B");
                mergeLists();
            }
        });
    }

    private void mergeLists() {
        Log.d("ViewModel", "Lista Ingredientes " + allIngredientsList.size());
        Log.d("ViewModel", "Lista Não Selecionados " + unselectedIngredients.getValue().size());
        Log.d("ViewModel", "Lista Selecionados " + selectedIngredients.getValue().size());
        Log.d("ViewModel", "----------------------");
        Log.d("Viewmodel", "");
        List<IngredientEntity> aux = new ArrayList<>(allIngredientsList);
        aux.removeAll(selectedIngredients.getValue());
        unselectedIngredients.setValue(aux);
    }

    public LiveData<List<IngredientEntity>> getUnselectedIngredients() {
        return unselectedIngredients;
    }

    public LiveData<List<IngredientEntity>> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void addSelectedIngredient(IngredientEntity ingredientEntity) {
        List<IngredientEntity> list = selectedIngredients.getValue();
        list.add(ingredientEntity);
        selectedIngredients.setValue(list);
    }

    public void removeSelectedIngredient(IngredientEntity ingredientEntity) {
        List<IngredientEntity> list = selectedIngredients.getValue();
        list.remove(ingredientEntity);
        selectedIngredients.setValue(list);

    }

//    public LiveData<List<IngredientEntity>> findAllIngredients() {
//        allIngredients = ingredientDao.findAllIngredients();
//        return allIngredients;
//    }

//    public LiveData<List<IngredientEntity>> unselectedIngredients() {
//        Log.d("viewmodel", "entrei unselected");
//        LiveData<List<IngredientEntity>> allIngredients = ingredientDao.findAllIngredients();
//        List<IngredientEntity> allIngredientsList = allIngredients.getValue();
//        List<IngredientEntity> selectedIngredientsList = selectedIngredients.getValue();
//        if (!selectedIngredientsList.isEmpty()) {
//            allIngredientsList.removeAll(selectedIngredientsList);
//        }
//        unselectedIngredients.setValue(allIngredientsList);
//        Log.d("viewmodel", "is null " + (unselectedIngredients == null));
//        return unselectedIngredients;
//    }

//    public void addSelectedIngredient(IngredientEntity ingredientEntity) {
//        List<IngredientEntity> list = selectedIngredients.getValue();
//        list.add(ingredientEntity);
//    }

}
