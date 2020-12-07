package com.vachan.service;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("infService")
public class InferenceService {

	private OntModel ontModel = loadOntModel();

	private Reasoner reasoner = ReasonerRegistry.getOWLReasoner();

	private Logger log = LoggerFactory.getLogger(InferenceService.class);
	private String output_dir = "/jena_output";

	private OntModel loadOntModel() {
		String resource = InferenceService.class.getClassLoader().getResource("harbour_politica_ontology_trimmed.owl")
				.getFile();
		OntModel ont_model = ModelFactory.createOntologyModel();
		try {
			ont_model.read(resource);
		} catch (Exception e) {

			log.error("Failed Loading Model from the owl file ", e);
		}

		return ont_model;
	}

	public void loadSingle() throws Exception {
		long start = System.currentTimeMillis();

		String resource = InferenceService.class.getClassLoader().getResource("single_node.xml").getFile();
		Model data = ModelFactory.createDefaultModel();
		data.read(resource);
		InfModel inf = ModelFactory.createInfModel(reasoner, ontModel, data);
		log.info("Created Inferred Model");
		OutputStream os = new FileOutputStream("jena_output_singlenode.xml");
		generateInferredModel(data, inf, os);
		log.info("Completed");
		os.flush();
		os.close();

		long end = System.currentTimeMillis();
		log.info("Time taken:" + (end - start) + "ms");
	}

	public void load500Nodes() throws Exception {
		long start = System.currentTimeMillis();

		String resource = InferenceService.class.getClassLoader().getResource("500_nodes.xml").getFile();
		Model data = ModelFactory.createDefaultModel();
		data.read(resource);
		InfModel inf = ModelFactory.createInfModel(reasoner, ontModel, data);
		log.info("Created Inferred Model");
		OutputStream os = new FileOutputStream("jena_output_500Nodes.xml");
		generateInferredModel(data, inf, os);
		log.info("Completed");
		os.flush();
		os.close();

		long end = System.currentTimeMillis();
		log.info("Time taken:" + (end - start) + "ms");
	}

	public void load2500Nodes() throws Exception {
		long start = System.currentTimeMillis();

		String resource = InferenceService.class.getClassLoader().getResource("2500_nodes.xml").getFile();
		Model data = ModelFactory.createDefaultModel();
		data.read(resource);
		InfModel inf = ModelFactory.createInfModel(reasoner, ontModel, data);
		log.info("Created Inferred Model");
		OutputStream os = new FileOutputStream("jena_output_2500Nodes.xml");
		generateInferredModel(data, inf, os);
		log.info("Completed");
		os.flush();
		os.close();

		long end = System.currentTimeMillis();
		log.info("Time taken:" + (end - start) + "ms");
	}

	public void load10kNodes() throws Exception {
		long start = System.currentTimeMillis();

		String resource = InferenceService.class.getClassLoader().getResource("10k_nodes.xml").getFile();
		Model data = ModelFactory.createDefaultModel();
		data.read(resource);
		InfModel inf = ModelFactory.createInfModel(reasoner, ontModel, data);
		log.info("Created Inferred Model");
		OutputStream os = new FileOutputStream("jena_output_10kNodes.xml");
		generateInferredModel(data, inf, os);
		log.info("Completed");
		os.flush();
		os.close();

		long end = System.currentTimeMillis();
		log.info("Time taken:" + (end - start) + "ms");
	}

	private void generateInferredModel(Model data, InfModel inf, OutputStream os) {
		RDFNode n = (RDFNode) null;
		Property p = inf.getProperty("urn:absolute://harbourpolitica/ontology/uri");
		Model result = ModelFactory.createDefaultModel();
		log.info("Parsing RDF type");
		ResIterator resIT = data.listResourcesWithProperty(RDF.type);
		while (resIT.hasNext()) {
			org.apache.jena.rdf.model.Resource rsc = resIT.next();

			StmtIterator it_main = inf.listStatements(rsc, RDF.type, n);
			while (it_main.hasNext()) {
				Statement stmt = it_main.next();
				result.add(stmt);
			}

			StmtIterator it_prop = inf.listStatements(rsc, p, n);
			while (it_prop.hasNext()) {
				Statement p_stmt = it_prop.next();
				result.add(p_stmt);
			}
		}

		log.info("Write to file");
		RDFDataMgr.write(os, result, Lang.RDFXML);
	}

}
