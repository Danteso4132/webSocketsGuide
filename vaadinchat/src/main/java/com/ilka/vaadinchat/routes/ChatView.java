package com.ilka.vaadinchat.routes;

import com.github.rjeschke.txtmark.Processor;
import com.ilka.vaadinchat.entity.Storage;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.shared.ui.Transport;


@Route("")
@Push
public class ChatView extends VerticalLayout implements AppShellConfigurator {

    private final Storage storage;
    private Registration registration;
    private Grid<Storage.ChatMessage> grid;

    private VerticalLayout chat;
    private VerticalLayout login;
    private String user = "";

    public ChatView(Storage storage) {
        this.storage = storage;
        buildLogin();
        buildChat();
        add(new Button("Game") {{
            addClickListener(click -> {
                getUI().get().getPage().setLocation("/game");
            });
        }});
    }

    private void buildLogin() {
        login = new VerticalLayout() {{
            TextField field = new TextField();
            field.setPlaceholder("Please, introduce yourself");
            add(
                    field,
                    new Button("Login") {{
                        addClickListener(click -> {
                            login.setVisible(false);
                            chat.setVisible(true);
                            user = field.getValue();
                            storage.addRecordJoined(user);
                        });
                        addClickShortcut(Key.ENTER);
                    }}
            );
        }};
        add(login);
    }

    private void buildChat() {
        chat = new VerticalLayout();
        add(chat);
        chat.setVisible(false);
        grid = new Grid<>();
        grid.setItems(storage.getMessages());
        grid.addColumn(new ComponentRenderer<>(message -> new Html(renderRow(message))))
                .setAutoWidth(true);

        TextField field = new TextField();
        chat.add(
                new H3("Vaadin ilka chat"),
                grid,
                new HorizontalLayout() {{
                    add(field,
                            new Button("➡") {{
                                addClickListener(click -> {
                                    storage.addRecord(user, field.getValue());
                                    field.clear();
                                });
                                addClickShortcut(Key.ENTER);
                            }}
                    );
                }}
        );
    }

    private String renderRow(Storage.ChatMessage message) {
        if (message.getName().isEmpty()) {
            return Processor.process(String.format("_User **%s** is just joined the chat!_", message.getMessage()));
        } else {
            return Processor.process(String.format("**%s**: %s", message.getName(), message.getMessage()));
        }
    }

    public void onMessage(Storage.ChatEvent event) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.getSession().lock();
            ui.beforeClientResponse(grid, ctx -> grid.scrollToEnd());
            ui.access(() -> grid.getDataProvider().refreshAll());
            ui.getSession().unlock();
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        registration = storage.attachListener(this::onMessage);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        registration.remove();
    }
}
