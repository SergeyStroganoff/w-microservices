package com.strtoganov.itemservice.service.item;

import com.strtoganov.itemservice.domain.model.item.*;
import com.strtoganov.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceIml implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ItemStyleService itemStyleService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private ManufactureService manufactureService;

    @Override
    public void saveAll(Set<Item> itemList) {
        itemRepository.saveAll(itemList);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public Optional<Item> getItemByArticleManufactureNameStyle(String modelArticle, String manufactureName, String styleArticle) {
        return itemRepository.findByModel_ArticleAndProducer_NameAndItemStyle_StyleArticle(modelArticle, manufactureName, styleArticle);
    }

    public Optional<Integer> getItemIdByArticleManufactureNameStyle(String modelArticle, String manufactureName, String styleArticle) {
        Integer itemID = itemRepository.findItemIdsByModelNameAndStyleArticle(modelArticle, manufactureName, styleArticle);
        return Optional.ofNullable(itemID);
    }

    @Override
    public List<Item> saveAllUnique(Set<Item> itemSet) {
        removeItemsIfPresentInDB(itemSet);
        if (itemSet.isEmpty()) {
            return Collections.emptyList();
        }
        Optional<ItemStyle> itemStyle;
        Optional<Manufacture> manufacture;
        Optional<Model> model;
        Optional<Dimension> dimension = Optional.empty();
        List<Item> itemList = new ArrayList<>(itemSet.size());
        for (Item item : itemSet) {
            itemStyle = itemStyleService.findItemStyleByStyleArticleAndStyleName(
                    item.getItemStyle().getStyleArticle(),
                    item.getItemStyle().getStyleName());
            model = modelService.findByArticleAndDescriptionAndDimension_WidthAndDimension_HeightAndDimension_Depth(
                    item.getModel().getArticle(),
                    item.getModel().getDescription(),
                    item.getModel().getDimension().getWidth(),
                    item.getModel().getDimension().getHeight(),
                    item.getModel().getDimension().getDepth());
            manufacture = manufactureService.findByNameAndDescription(
                    item.getProducer().getName(),
                    item.getProducer().getDescription());
            if (model.isEmpty()) {
                dimension = dimensionService.findDimensionByWidthAndHeightAndDepth(
                        item.getModel().getDimension().getWidth(),
                        item.getModel().getDimension().getHeight(),
                        item.getModel().getDimension().getDepth()
                );
            }
            itemStyle.ifPresent(item::setItemStyle);
            model.ifPresent(item::setModel);
            manufacture.ifPresent(item::setProducer);
            dimension.ifPresent(item.getModel()::setDimension);
            Item savedItem = itemRepository.save(item);
            itemList.add(savedItem);
        }
        return itemList;
    }

    @Override
    public Optional<Item> findItemByModelNameAndStyleArticle(String article, String producerName, String styleArticle) {
        return itemRepository.findByModel_ArticleAndProducer_NameAndItemStyle_StyleArticle(
                article, producerName, styleArticle);
    }

    @Override
    public Optional<Integer> findItemIdByModelNameAndStyleArticle(String article, String producerName, String styleArticle) {
        return Optional.ofNullable(itemRepository.findItemIdsByModelNameAndStyleArticle(article, producerName, styleArticle));
    }

    private void removeItemsIfPresentInDB(Set<Item> itemSet) {
        Iterator<Item> itemIterator = itemSet.iterator();
        while (itemIterator.hasNext()) {
            Item currentItem = itemIterator.next();
            boolean isItemPresent = itemRepository.existsByModel_ArticleAndProducer_NameAndItemStyle_StyleArticle(
                    currentItem.getModel().getArticle(),
                    currentItem.getProducer().getName(),
                    currentItem.getItemStyle().getStyleArticle());
            if (isItemPresent) {
                itemIterator.remove();
            }
        }
    }

    private void removeItemsIfPresentInDB_V2(Set<Item> itemSet) {
        Iterator<Item> itemIterator = itemSet.iterator();
        while (itemIterator.hasNext()) {
            Item currentItem = itemIterator.next();
            Optional<Integer> currentOptionalItem = getItemIdByArticleManufactureNameStyle(  //getItemByArticleManufactureNameStyle
                    currentItem.getModel().getArticle(),
                    currentItem.getProducer().getName(),
                    currentItem.getItemStyle().getStyleArticle());
            if (currentOptionalItem.isPresent()) {
                itemIterator.remove();
            }
        }
    }
}
