package com.example.healthy_way.service.impl;

import com.example.healthy_way.model.binding.RecipeBindingModel;
import com.example.healthy_way.model.entity.Recipe;
import com.example.healthy_way.model.view.RecipeView;
import com.example.healthy_way.repository.RecipeRepository;
import com.example.healthy_way.service.RecipeService;
import com.example.healthy_way.service.UserService;
import com.example.healthy_way.utils.AuthProvider;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserService userService, ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RecipeView> getAll() {
        return this.recipeRepository.findAll()
                .stream()
                .map(r -> modelMapper.map(r,RecipeView.class))
                .collect(Collectors.toList());
    }

    @Override
    public RecipeView getById(String id) {
        return modelMapper.map(this.recipeRepository.getById(id),RecipeView.class);
    }

    @Override
    public void addRecipe(RecipeBindingModel recipeBindingModel) {
        Recipe recipe = modelMapper.map(recipeBindingModel, Recipe.class);
        recipe.setCreatedOn(LocalDateTime.now());

        recipe.setAuthor(this.userService.findUserByUsername(AuthProvider.getAuth().getName()));

        this.recipeRepository.save(recipe);
    }

    @Override
    public Recipe findById(String recipeId) {
        return this.recipeRepository.findById(recipeId).orElseThrow();
    }
}
