-- A	5865	5845	5825	5805	5785	5765	5745	5725

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'A', 'Boscam A');

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5865, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5845, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5825, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5805, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5785, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5765, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5745, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5725, currval('band_seq'));

-- B	5733	5752	5771	5790	5809	5828	5847	5866

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'B', 'Boscam B');

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5733, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5752, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5771, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5790, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5809, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5828, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5847, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5866, currval('band_seq'));

-- E	5705	5685	5665	5645	5885	5905	5925	5945

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'E', 'Boscam E');

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5705, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5685, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5665, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5645, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5885, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5905, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5925, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5945, currval('band_seq'));

-- F	5740	5760	5780	5800	5820	5840	5860	5880

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'F', 'Fatshark');

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5740, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5760, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5780, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5800, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5820, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5840, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5860, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5880, currval('band_seq'));

-- R	5658	5695	5732	5769	5806	5843	5880	5917

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'R', 'Raceband');

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5658, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5695, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5732, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5769, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5806, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5843, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5880, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5917, currval('band_seq'));

-- Diatone	5362	5399	5436	5473	5510	5547	5584	5621

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'Diatone', 'Diatone');

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5362, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5399, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5436, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5473, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5510, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5547, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5584, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5621, currval('band_seq'));


-- U	5325	5348	5366	5384	5402	5420	5438	5456

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'U', null);

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5325, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5348, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5366, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5384, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5402, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5420, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5438, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5456, currval('band_seq'));


-- O	5474	5492	5510	5528	5546	5564	5582	5600

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'O', null);

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5474, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5492, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5510, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5528, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5546, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5564, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5582, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5600, currval('band_seq'));

-- L	5333	5373	5413	5453	5493	5533	5573	5613

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'L', null);

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5333, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5373, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5413, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5453, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5493, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5533, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5573, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5613, currval('band_seq'));

-- H	5653	5693	5733	5773	5813	5853	5893	5933

INSERT INTO band (id, name, description)
VALUES (nextval('band_seq'), 'H', null);

INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 1, 1, 5653, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 2, 2, 5693, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 3, 3, 5733, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 4, 4, 5773, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 5, 5, 5813, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 6, 6, 5853, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 7, 7, 5893, currval('band_seq'));
INSERT INTO channel (id, number, ordinal, frequency, band_id)
VALUES (nextval('channel_seq'), 8, 8, 5933, currval('band_seq'));
