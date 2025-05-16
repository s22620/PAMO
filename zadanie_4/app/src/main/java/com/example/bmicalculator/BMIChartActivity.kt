package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

class BMIChartActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    // BMI - progres użytkownika
    private val bmiData = listOf(
        BMIEntry("2025-01-15", 27.3f),
        BMIEntry("2025-02-15", 26.8f),
        BMIEntry("2025-03-15", 26.6f),
        BMIEntry("2025-04-15", 25.9f),
        BMIEntry("2025-05-15", 25.8f)
    )

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_chart_webview)

        webView = findViewById(R.id.webViewChart)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        val htmlContent = generateChartHtml()
        webView.loadDataWithBaseURL(
            "https://www.google.com",
            htmlContent,
            "text/html",
            "UTF-8",
            null
        )
    }

    private fun generateChartHtml(): String {
        val dataArray = JSONArray()

        val headers = JSONArray()
        headers.put("Data")
        headers.put("BMI")
        dataArray.put(headers)

        for (entry in bmiData) {
            val row = JSONArray()
            row.put(formatDate(entry.date))
            row.put(entry.bmiValue)
            dataArray.put(row)
        }

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = google.visualization.arrayToDataTable(${dataArray});

                var options = {
                    title: 'Your BMI change chart',
                    titleTextStyle: {
                        fontSize: 20,
                        bold: true,
                        color: '#333'
                    },
                    curveType: 'function',
                    legend: { position: 'bottom' },
                    hAxis: {
                        title: 'Data',
                        titleTextStyle: { italic: true },
                        textStyle: { fontSize: 12 }
                    },
                    vAxis: {
                        title: 'BMI',
                        viewWindow: {
                            min: 20,
                            max: 30
                        },
                        ticks: [
                            {v: 18.5, f: '18.5 - Underweight'},
                            {v: 25, f: '25 - Overweight'},
                            {v: 30, f: '30 - Obesity'}
                        ]
                    },
                    colors: ['#FFC107'],
                    lineWidth: 3,
                    pointSize: 5,
                    backgroundColor: '#ffffff'
                };

                var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
                chart.draw(data, options);
            }
        </script>
        <style>
            body, html {
                margin: 0;
                padding: 0;
                width: 100%;
                height: 100%;
                font-family: sans-serif;
                background: #fff;
            }
            #chart_div {
                width: 100%;
                height: 100%;
                padding: 16px;
                margin-top: 40px; 
                box-sizing: border-box;
            }
        </style>
    </head>
    <body>
        <div id="chart_div"></div>
    </body>
    </html>
""".trimIndent()

    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString) ?: return dateString
        return outputFormat.format(date)
    }

    data class BMIEntry(val date: String, val bmiValue: Float)
}
