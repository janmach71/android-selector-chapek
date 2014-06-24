/*
 * Copyright 2013 Inmite s.r.o. (www.inmite.eu).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import utils.Log;
import utils.RunnableHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles detection of selector files from resources and creation of those files.
 * @author David Vávra (david@inmite.eu)
 */
public class SelectorDetector {

	private Project mProject;
	private List<String> mCreatedFiles;

	public SelectorDetector(Project project) {
		mProject = project;
		mCreatedFiles = new ArrayList<String>();
	}

	public void detectAndCreateSelectors(VirtualFile selectedFolder) {
		VirtualFile[] children = selectedFolder.getChildren();
		for (VirtualFile child : children) {
			Log.d("processing " + child.getName());
			List<Pattern> suffixes = detectSuffixes(child);
			Log.d("suffixes: " + suffixes);
			if (suffixes.size() > 0) {
				for (VirtualFile other : children) {
					if (matches(child, other)) {
						Log.d("matches: " + child.getName() + " and " + other.getName());
						createFile(child, selectedFolder, suffixes);
						break;
					}
				}
			}
		}
	}

	private static boolean matches(VirtualFile first, VirtualFile second) {
		List<Pattern> suffixes1 = detectSuffixes(first);
		List<Pattern> suffixes2 = detectSuffixes(second);
		if (suffixes1.size() == 0 || suffixes2.size() == 0 || suffixes1.equals(suffixes2)) {
			return false;
		}
		String firstWithoutSuffix = removeSuffixes(first.getName(), suffixes1);
		String secondWithoutSuffix = removeSuffixes(second.getName(), suffixes2);
		return firstWithoutSuffix.equals(secondWithoutSuffix);
	}

	private static String removeSuffixes(String name, List<Pattern> suffixes) {
		for (Pattern suffix : suffixes) {
            Matcher matcher = suffix.matcher(name);
            if (matcher.matches()) {
                name=matcher.replaceFirst(Constants.REPLACE_WITH);
                break;
            }
		}
		return name;
	}

	private void createFile(final VirtualFile file, final VirtualFile folder, List<Pattern> fileSuffixes) {
		final String fileName = removeSuffixes(file.getName(), fileSuffixes).replace(".9.png", ".xml").replace(".png",
				".xml");
		if (mCreatedFiles.contains(fileName)) {
			Log.d("skipping, already generated");
			return;
		}
		mCreatedFiles.add(fileName);
		RunnableHelper.runWriteCommand(mProject, new Runnable() {
			@Override
			public void run() {
				try {
					VirtualFile drawableFolder = folder.getParent().findChild(Constants.EXPORT_FOLDER);
					if (drawableFolder == null || !drawableFolder.exists()) {
						drawableFolder = folder.getParent().createChildDirectory(null, Constants.EXPORT_FOLDER);
						Log.d("drawable folder created");
					}
					Log.d("trying to create: " + drawableFolder + " " + fileName);
					VirtualFile newFile = drawableFolder.findChild(fileName);
					if (newFile == null || !newFile.exists()) {
						newFile = drawableFolder.createChildData(null, fileName);
						Log.d("file created: " + fileName);
						SelectorGenerator.generate(newFile, detectStates(file, folder));
					} else {
						Log.d("skipping, file already exists:" + fileName);
					}
				} catch (IOException e) {
					Log.e(e);
				}
			}
		});
	}

	private static List<Pattern> detectSuffixes(VirtualFile child) {
		List<Pattern> suffixes = new ArrayList<Pattern>();
		for (Pattern suffix : Constants.SUFFIXES) {
            Matcher matcher = suffix.matcher(child.getName());
            if (matcher.matches()) {
				suffixes.add(suffix);
			}
		}
		return suffixes;
	}

	private static List<Result> detectStates(VirtualFile file, VirtualFile folder) {
		List<Result> results = new ArrayList<Result>();
		results.add(new Result(detectSuffixes(file), removeFileEndings(file.getName())));
		for (VirtualFile other : folder.getChildren()) {
			if (matches(file, other)) {
				results.add(new Result(detectSuffixes(other), removeFileEndings(other.getName())));
			}
		}
		return results;
	}

	private static String removeFileEndings(String fileName) {
		return fileName.replace(".9.png", "").replace(".png", "");
	}

	static class Result {
		public List<Pattern> states;
		public String drawableName;

		Result(List<Pattern> states, String drawableName) {
			this.states = states;
			this.drawableName = drawableName;
		}
	}
}
