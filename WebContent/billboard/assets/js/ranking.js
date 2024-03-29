import { getRanking } from "./assets/js/get-ranking.js";
const useState = React.useState;
const useEffect = React.useEffect;

const Ranking = () => {
  const [albums, setAlbums] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    (async () => {
      setIsLoading(true);
      const ranking = await getRanking();
      ranking
        ? setAlbums(ranking.data)
        : setError("ランキングの取得に失敗しました");
      setIsLoading(false);
    })();
  }, []);

  return (
    <>
      <Header />
      <div className="page-container">
        <div className="spotify-container">
          <h1>BILLBOARD CHART</h1>
          <a
            href="https://open.spotify.com/playlist/1EYHrEKAHQKKLdmF4W5dYl"
            target="_blank"
            rel="noopener noreferrer"
          >
            <img
              src="assets/img/listen_on_spotify_logo.png"
              alt="Listen on Spotify"
              className="listen-on-spotify"
            ></img>
          </a>
        </div>
        <table>
          {albums &&
            albums.map((album, index) => (
              <Album album={album} rank={index + 1} />
            ))}
        </table>
        <div>
          {isLoading && <p className="loader">Loading...</p>}
          {error && <p>{error}</p>}
        </div>
      </div>
    </>
  );
};

const Album = ({ album, rank }) => {
  return (
    <tr>
      <th>
        <p>{rank}</p>
      </th>
      <td className="table-img">
        <img
          src={
            album.album_image_url !== "null"
              ? album.album_image_url
              : "./assets/img/entertainment_music.png"
          }
          alt={album.album_name}
          className="ranking-track-image"
        />
      </td>
      <td>
        <p title={album.track_name} className="table-track-name">
          {album.track_name}
        </p>
        <p title={album.artist_name} className="table-artist-name">
          {album.artist_name}
        </p>
        <span className="table-points">{album.access} points</span>
      </td>
    </tr>
  );
};

const Header = () => {
  return (
    <header>
      <a href="index.html" className="logo">
        Billboard Web App
      </a>
      <nav>
        <ul className="nav-list">
          <li className="nav-list-item">
            <a href="index.html">ホーム</a>
          </li>
          <li clasName="nav-list-item">
            <a href="search.html">検索</a>
          </li>
        </ul>
      </nav>
    </header>
  );
};

const container = document.getElementById("root");
const root = ReactDOM.createRoot(container);
root.render(<Ranking />);
