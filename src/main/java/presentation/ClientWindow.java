package presentation;

import javax.swing.*;

public class ClientWindow extends JFrame{
    private JPanel mainPanel;
    private JPanel searchAndOrderPanel;
    private JScrollPane productsPane;
    private JPanel filtersPane;
    private JTable productsTable;
    private JButton searchButton;
    private JCheckBox titleCheckBox;
    private JTextField searchTitleField;
    private JCheckBox ratingCheckBox;
    private JTextField searchRatingField;
    private JTextField searchCaloriesField;
    private JCheckBox caloriesCheckBox;
    private JCheckBox proteinCheckBox;
    private JTextField searchProteinField;
    private JTextField searchFatField;
    private JTextField searchSodiumField;
    private JTextField searchPriceField;
    private JCheckBox fatsCheckBox;
    private JCheckBox sodiumCheckBox;
    private JCheckBox priceCheckBox;
    private JPanel myCartPanel;
    private JButton goToCartButton;
    private JTable chosenProductsTable;
    private JButton finishOrderButton;
    private JButton discardOrderButton;
    private JButton addMoreItemsButton;
    private JButton deleteSelectedProductButton;
    private JButton addSelectedButton;
    private ClientController clientController;

    public ClientWindow() {
        setContentPane(mainPanel);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Client operations");
        clientController = new ClientController(this);

        searchAndOrderPanel.setVisible(true);
        myCartPanel.setVisible(false);
        goToCartButton.addActionListener(clientController);
        addMoreItemsButton.addActionListener(clientController);
        searchButton.addActionListener(clientController);
    }

    public JPanel getSearchAndOrderPanel() {
        return searchAndOrderPanel;
    }

    public JScrollPane getProductsPane() {
        return productsPane;
    }

    public JPanel getFiltersPane() {
        return filtersPane;
    }

    public JTable getProductsTable() {
        return productsTable;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JCheckBox getTitleCheckBox() {
        return titleCheckBox;
    }

    public JTextField getSearchTitleField() {
        return searchTitleField;
    }

    public JCheckBox getRatingCheckBox() {
        return ratingCheckBox;
    }

    public JTextField getSearchRatingField() {
        return searchRatingField;
    }

    public JTextField getSearchCaloriesField() {
        return searchCaloriesField;
    }

    public JCheckBox getCaloriesCheckBox() {
        return caloriesCheckBox;
    }

    public JCheckBox getProteinCheckBox() {
        return proteinCheckBox;
    }

    public JTextField getSearchProteinField() {
        return searchProteinField;
    }

    public JTextField getSearchFatField() {
        return searchFatField;
    }

    public JTextField getSearchSodiumField() {
        return searchSodiumField;
    }

    public JTextField getSearchPriceField() {
        return searchPriceField;
    }

    public JCheckBox getFatsCheckBox() {
        return fatsCheckBox;
    }

    public JCheckBox getSodiumCheckBox() {
        return sodiumCheckBox;
    }

    public JCheckBox getPriceCheckBox() {
        return priceCheckBox;
    }

    public JPanel getMyCartPanel() {
        return myCartPanel;
    }

    public JButton getGoToCartButton() {
        return goToCartButton;
    }

    public JTable getChosenProductsTable() {
        return chosenProductsTable;
    }

    public JButton getFinishOrderButton() {
        return finishOrderButton;
    }

    public JButton getDiscardOrderButton() {
        return discardOrderButton;
    }

    public JButton getAddMoreItemsButton() {
        return addMoreItemsButton;
    }

    public JButton getDeleteSelectedProductButton() {
        return deleteSelectedProductButton;
    }

    public ClientController getClientController() {
        return clientController;
    }
}
