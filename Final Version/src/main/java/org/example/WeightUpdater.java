package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WeightUpdater {

    public static double[] calculateMeanWeights(String filename) throws IOException {
        double[] meanWeights = null;
        int numWeights = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] weights = line.trim().split("\\s+");
                if (meanWeights == null) {
                    numWeights = weights.length;
                    meanWeights = new double[numWeights];
                }
                for (int i = 0; i < numWeights; i++) {
                    meanWeights[i] += Double.parseDouble(weights[i]);
                }
            }
        }

        if (meanWeights != null) {
            for (int i = 0; i < numWeights; i++) {
                meanWeights[i] /= numWeights;
            }
        }

        return meanWeights;
    }

    public static void updateWeightsFile(String filename, double[] weights) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (double weight : weights) {
                writer.write(Double.toString(weight));
                writer.newLine();
            }
        }
    }
}
