import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Weather Forecast");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        addGuiComponents(contentPanel);
    }

    private void addGuiComponents(JPanel contentPanel) {
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 24));
        searchTextField.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
        contentPanel.add(searchTextField);

        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy[1].png"));
        weatherConditionImage.setBounds(0, 75, 450, 217);
        contentPanel.add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 F°");
        temperatureText.setBounds(0, 300, 450, 54);
        temperatureText.setFont(new Font("Arial", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(temperatureText);

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 360, 450, 36);
        weatherConditionDesc.setFont(new Font("Arial", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(weatherConditionDesc);

        JPanel infoPanel = new JPanel();
        infoPanel.setBounds(15, 410, 420, 80);
        infoPanel.setLayout(new GridLayout(1, 2));

        JLabel humidityLabel = new JLabel("<html><b>Humidity:</b> <span style='color:gray;'>100%</span></html>");
        humidityLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel windspeedLabel = new JLabel("<html><b>Windspeed:</b> <span style='color:gray;'>15 km/h</span></html>");
        windspeedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(humidityLabel);
        infoPanel.add(windspeedLabel);

        contentPanel.add(infoPanel);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(375, 15, 60, 45);
        searchButton.setBackground(Color.ORANGE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setFocusPainted(false);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();
                if (userInput.trim().isEmpty()) {
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);

                String weatherCondition = (String) weatherData.get("weather_condition");
                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear[1].png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy[1].png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain[1].png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow[1].png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " F°");

                weatherConditionDesc.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityLabel.setText("<html><b>Humidity:</b> <span style='color:gray;'>" + humidity + "%</span></html>");

                double windspeed = (double) weatherData.get("windspeed");
                windspeedLabel.setText("<html><b>Windspeed:</b> <span style='color:gray;'>" + windspeed + " km/h</span></html>");
            }
        });

        contentPanel.add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not find resource");
            return null;
        }
    }
}

