package be.vdab.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.mail.MailSender;
import be.vdab.repositories.FiliaalRepository;
import be.vdab.valueobjects.PostcodeReeks;

@ReadOnlyTransactionalService
public class DefaultFiliaalService implements FiliaalService {
	private final FiliaalRepository filiaalRepository;
	private final MailSender mailSender;
	private final JmsMessagingTemplate jmsMessagingTemplate;

	DefaultFiliaalService(FiliaalRepository filiaalDAO, MailSender mailSender,
			JmsMessagingTemplate jmsMessagingTemplate) {
		this.filiaalRepository = filiaalDAO;
		this.mailSender = mailSender;
		this.jmsMessagingTemplate = jmsMessagingTemplate;
	}

	@Override
	@ModifyingTransactionalServiceMethod
	public void create(Filiaal filiaal, String urlAlleFilialen) {
		filiaalRepository.save(filiaal);
		mailSender.nieuwFiliaalMail(filiaal, urlAlleFilialen + '/' + filiaal.getId());
		MessageBuilder<String> builder = MessageBuilder.withPayload(urlAlleFilialen + '/' + filiaal.getId());
		jmsMessagingTemplate.send(builder.build());
	}

	@Override
	public Optional<Filiaal> read(long id) {
		return Optional.ofNullable(filiaalRepository.findOne(id));
	}

	@Override
	@ModifyingTransactionalServiceMethod
	public void update(Filiaal filiaal) {
		filiaalRepository.save(filiaal);
	}

	@Override
	@ModifyingTransactionalServiceMethod
	public void delete(long id) {
		Optional<Filiaal> optionalFiliaal = Optional.ofNullable(filiaalRepository.findOne(id));
		if (optionalFiliaal.isPresent()) {
			if (!optionalFiliaal.get().getWerknemers().isEmpty()) {
				throw new FiliaalHeeftNogWerknemersException();
			}
			filiaalRepository.delete(id);
		}
	}

	@Override
	public List<Filiaal> findAll() {
		return filiaalRepository.findAll(new Sort("naam"));
	}

	@Override
	public long findAantalFilialen() {
		return filiaalRepository.count();
	}

	@Override
	public List<Filiaal> findByPostcodeReeks(PostcodeReeks reeks) {
		return filiaalRepository.findByAdresPostcodeBetweenOrderByNaam(reeks.getVanpostcode(), reeks.getTotpostcode());
	}

	@Override
	public List<Filiaal> findNietAfgeschreven() {
		return filiaalRepository.findByWaardeGebouwNot(BigDecimal.ZERO);
	}

	@Override
	@ModifyingTransactionalServiceMethod
	public void afschrijven(List<Filiaal> filialen) {
		filialen.forEach(filiaal -> filiaal.afschrijven());
	}
}
