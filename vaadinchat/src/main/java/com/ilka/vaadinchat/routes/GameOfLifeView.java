package com.ilka.vaadinchat.routes;

import com.ilka.vaadinchat.entity.Storage;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

@Route("/game")
public class GameOfLifeView extends VerticalLayout {

    public GameOfLifeView() {
        VerticalLayout verticalLayout = new VerticalLayout(
                new H3("Game of life")
        );
        add(verticalLayout);
    }
}
