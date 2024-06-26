package montecarlo_simulation.sample_implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import montecarlo_simulation.app.MonteCarloSimulation.SimulationResult;
import montecarlo_simulation.app.SimulationResultExporter;

/**
 * シミュレーション結果の保存
 */
public class SampleSimulationResultExporter implements SimulationResultExporter {

	private File rootDir;

	public SampleSimulationResultExporter(File rootDir) {
		this.rootDir = rootDir;
		rootDir.mkdirs();
	}

	/**
	 * シミュレーション結果をエクスポートする
	 * 
	 * @param results シミュレーション結果
	 */
	@Override
	public void exportSimulationResult(List<SimulationResult> results) {
		long exportStartTime = System.currentTimeMillis();

		results.parallelStream().forEach(r -> {
			File file = new File(rootDir, UUID.randomUUID().toString() + ".txt");
			file.deleteOnExit();
			try {
				Files.write(file.toPath(), r.getPrices().toString().getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		System.out.println("export time:" + (System.currentTimeMillis() - exportStartTime));
	}
}
