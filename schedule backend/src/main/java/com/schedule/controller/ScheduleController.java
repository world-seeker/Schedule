package com.schedule.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.schedule.exception.ResourceNotFoundException;
import com.schedule.model.ImageModel;
import com.schedule.model.Schedule;
import com.schedule.repository.ImageRepository;
import com.schedule.repository.ScheduleRepository;


@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	//getAll users
	@GetMapping("/schedules")
	public List<Schedule> getAllUsers()
	{
		return scheduleRepository.findAll();	
	}
    
	//create user restApi
    @PostMapping("/schedules")
	public Schedule createSchedule(@RequestBody Schedule schedule)
    {
    	
		return scheduleRepository.save(schedule);
		
	}
    
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Object> deleteSchedule(@PathVariable Long id) {
        if (scheduleRepository.existsById(id)) {
            scheduleRepository.deleteById(id);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Delete successful"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Schedule not found"));
        }
    }

    
    
    // Image Uploader and getter
    
    @Autowired
	ImageRepository imageRepository;

	@PostMapping("/upload")
	public ResponseEntity<String> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		 try {
		        // Check if a file with the same name already exists
		        Optional<ImageModel> existingImage = imageRepository.findByName(file.getOriginalFilename());

		        if (existingImage.isPresent()) {
		            // Handle the case where the file already exists (you can update or ignore)
		            // For now, we'll return a message indicating the duplication
		            return ResponseEntity.status(HttpStatus.CONFLICT).body("File with the same name already exists");
		        }
	    System.out.println("Original Image Byte Size - " + file.getBytes().length);
	    ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
	    imageRepository.save(img);
	    return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully");
		  } catch (IOException e) {
		        // Handle exceptions, e.g., if there's an issue with file processing
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
		    }
	}

	@GetMapping(path = { "/get/{imageName}" })
	public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {

		final Optional<ImageModel> retrievedImage = imageRepository.findByName(imageName);
		final ImageModel imageModel = retrievedImage.orElseThrow(() -> new ResourceNotFoundException("Not Present"));

		ImageModel img = new ImageModel(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}
	
	


	

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	
}
