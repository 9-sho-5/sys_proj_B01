export const postMusic = async (
  trackId,
  trackName,
  artistName,
  albumName,
  albumImageURL
) => {
  const postData = {
    trackId: trackId,
    track_name: trackName,
    artist_name: artistName,
    album_name: albumName,
    album_image_url: albumImageURL,
  };
  const url = "/billboard/add_track";
  const param = {
    method: "POST",
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
    body: JSON.stringify(postData),
  };
  const response = await fetch(url, param);
  console.log(response);
  if (response.ok) return true;
  else return false;
};
